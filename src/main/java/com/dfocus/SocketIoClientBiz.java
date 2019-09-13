package com.dfocus;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.dfocus.error.InvalidArgumentException;
import com.dfocus.error.LifecycleException;
import com.dfocus.factories.SocketIOFactory;
import com.dfocus.socket.AuthCode;
import com.dfocus.socket.BizEvent;
import com.dfocus.socket.ClientState;
import com.dfocus.socket.EventCallback;
import com.dfocus.socket.EventMessage;
import com.dfocus.socket.EventStruct;
import com.dfocus.socket.Finish;
import com.dfocus.socket.StateChangeCallback;
import com.dfocus.socket.SubscribeCode;
import com.dfocus.socket.Subscription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIoClientBiz {

	final static List<String> DISCONNECT_EVENTS = Arrays.asList(Socket.EVENT_CONNECT_ERROR,
			Socket.EVENT_CONNECT_TIMEOUT, Socket.EVENT_DISCONNECT, Socket.EVENT_ERROR, Socket.EVENT_RECONNECT_ERROR,
			Socket.EVENT_RECONNECT_FAILED);

	private SocketIOFactory factory;
	private Socket socket;
	private ClientState state;

	private List<StateChangeCallback> stateChangeCallbacks;

	private List<EventStruct> events;

	public SocketIoClientBiz(SocketIOFactory factory) {
		this.factory = factory;

		this.stateChangeCallbacks = new ArrayList<StateChangeCallback>();
		this.events = new ArrayList<EventStruct>();
		this.state = ClientState.DISCONNECTED;
	}

	public SocketIoClientBiz connect(Finish onConnected) throws URISyntaxException, LifecycleException {
		if (socket != null) {
			throw new LifecycleException("You cannot call connect multiple times");
		}

		connectToWebsocket(onConnected);

		return this;
	}

	private void connectToWebsocket(final Finish onConnected) throws URISyntaxException {
		socket = factory.get();

		System.out.println("Trying to connect to ssp Server...");

		socket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				changeState(ClientState.CONNECTED);
			}
		});

		socket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				changeState(ClientState.CONNECTING);
			}
		});

		socket.on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				changeState(ClientState.CONNECTING);
			}
		});

		final Emitter.Listener errorListener = new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				onConnected.onFinished(args[0].toString());
			}
		};

		socket.on(Socket.EVENT_ERROR, errorListener);

		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

				final Socket localSoc = socket;
				System.out.println("EVENT_CONNECT ");
				// handshake for authentication purpose
				socket.emit(BizEvent.AUTH.toString(), factory.getAuthData(), new Ack() {

					@Override
					public void call(Object... authCode) {

						AuthCode code = AuthCode.from((String) authCode[0]);

						System.out.println("Handshake status " + code);

						// failed to auth, disconnect and won't retry
						if (AuthCode.AUTH_FAILED.equals(code)) {
							onConnected.onFinished(code.toString());
							disconnect();
							return;
						}
						socket.off(Socket.EVENT_ERROR, errorListener);

						
						onConnected.onFinished("");

						DISCONNECT_EVENTS.forEach(new Consumer<String>() {

							public void accept(String e) {
								socket.on(e, new Emitter.Listener() {

									@Override
									public void call(Object... args) {
										changeState(ClientState.DISCONNECTED);
										endProcess(localSoc);
									}
								});
							}
						});

						startProcess();

						changeState(ClientState.CONNECTED);
					}
				});
			}
		});

		socket.connect();
	}

	public SocketIoClientBiz disconnect() {
		try {
			changeState(ClientState.DISCONNECTED);

			// stateChangeCallbacks.clear();
			// events.clear();

			Socket s = this.socket;
			socket = null;

			s.close();
		}
		catch (Exception error) {
			System.err.println(error.getMessage());
		}
		return this;
	}

	public Subscription onStateChange(final StateChangeCallback cb) {
		if (cb == null) {
			return new Subscription() {
				@Override
				public void dispose() {
				}
			};
		}

		if (!this.stateChangeCallbacks.contains(cb)) {
			this.stateChangeCallbacks.add(cb);
		}

		return new Subscription() {
			@Override
			public void dispose() {
				stateChangeCallbacks.remove(cb);
			}
		};
	}

	private void changeState(final ClientState state) {
		this.state = state;

		stateChangeCallbacks.forEach(new Consumer<StateChangeCallback>() {
			public void accept(StateChangeCallback cb) {
				cb.onChange(state);
			}
		});
	}

	public Subscription subscribe(final String topic, final String event, final EventCallback callback) throws InvalidArgumentException, LifecycleException {
		if (topic == null || event == null || callback == null) {
			throw new InvalidArgumentException("topic or event or callback cannot be empty");
		}

		if (ClientState.CONNECTED == state) {
			throw new LifecycleException("subscribe cannot be called after connection established");
		}

		final Emitter.Listener eventListener = new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				try {
					String strObj = args[0].toString();
					JSONObject obj = new JSONObject(strObj);
					EventMessage message;
					message = new EventMessage(obj.getString("projectId"), obj.getString("topic"),
							obj.getString("event"), obj.getString("payload"));
					if (topic.equals(message.getTopic())) {
						callback.onFire(message);
					}
				}
				catch (JSONException err) {
					System.err.println(err);
				}

			}
		};

		events.add(new EventStruct(topic, event, eventListener));

		return new Subscription() {
			@Override
			public void dispose() {
				socket.off(event, eventListener);
			}
		};
	}

	private void startProcess() {
		JSONArray topics = new JSONArray();
		for (EventStruct e : events) {
			topics.put(e.getTopic());
		}
		subscribe(events, topics);
	}

	private void subscribe(final List<EventStruct> events, JSONArray topics) {
		socket.emit(BizEvent.SUBSCRIBE.toString(), topics, new Ack() {

			@Override
			public void call(Object... args) {

				SubscribeCode code = SubscribeCode.from((String) args[0]);

				System.out.println("subscribe ack code: " + code);
				if (!SubscribeCode.SUB_SUCCESS.equals(code)) {
					// do nothing if it is not allowed
					return;
				}

				events.forEach(new Consumer<EventStruct>() {
					public void accept(final EventStruct e) {
						socket.on(e.getEvent(), e.getCallback());
					}
				});
			}
		});
	}

	private void endProcess(final Socket socket) {
		this.events.forEach(new Consumer<EventStruct>() {
			public void accept(EventStruct e) {
				socket.off(e.getEvent());
			}
		});

		DISCONNECT_EVENTS.forEach(new Consumer<String>() {
			public void accept(String e) {
				socket.off(e);
			}
		});
	}

}
