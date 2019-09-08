package com.dfocus;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.dfocus.socket.AuthCode;
import com.dfocus.socket.AuthStruct;
import com.dfocus.socket.BizEvent;
import com.dfocus.socket.ClientState;
import com.dfocus.socket.EventCallback;
import com.dfocus.socket.EventMessage;
import com.dfocus.socket.EventStruct;
import com.dfocus.socket.Finish;
import com.dfocus.socket.Helper;
import com.dfocus.socket.SocketOpts;
import com.dfocus.socket.StateChangeCallback;
import com.dfocus.socket.SubscribeCode;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.client.IO.Options;
import io.socket.emitter.Emitter;

public class SocketIoClientBiz {

    final static List<String> DISCONNECT_EVENTS = Arrays.asList(Socket.EVENT_CONNECT_ERROR,
            Socket.EVENT_CONNECT_TIMEOUT, Socket.EVENT_DISCONNECT, Socket.EVENT_ERROR, Socket.EVENT_RECONNECT_ERROR,
            Socket.EVENT_RECONNECT_FAILED);

    private SocketOpts opts;
    private Socket socket;
    private List<StateChangeCallback> stateChangeCallbacks;
    private List<EventStruct> events;

    public SocketIoClientBiz(SocketOpts opts) {
        this.opts = opts;

        this.stateChangeCallbacks = new ArrayList<StateChangeCallback>();
        this.events = new ArrayList<EventStruct>();
    }

    public void connect(Finish onConnected) throws URISyntaxException {
        if (socket != null) {
            throw new RuntimeException("You cannot call connect multiple times");
        }

        connectToWebsocket(onConnected);
    }

    private void connectToWebsocket(final Finish onConnected) throws URISyntaxException {
        socket = IO.socket(Helper.toUrl(opts.getBase(), opts.getProjectId()), getSocketOptions());

        changeState(ClientState.CONNECTING);
        System.out.println("Trying to connect to ssp Server...");

        DISCONNECT_EVENTS.forEach(new Consumer<String>() {

            public void accept(String e) {
                socket.on(e, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        changeState(ClientState.DISCONNECTED);
                        endProcess();
                    }
                });
            }
        });

        this.socket.on(Socket.EVENT_RECONNECT.toString(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeState(ClientState.CONNECTED);
            }
        });

        this.socket.on(Socket.EVENT_CONNECTING.toString(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeState(ClientState.CONNECTING);
            }
        });

        this.socket.on(Socket.EVENT_RECONNECTING.toString(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeState(ClientState.CONNECTING);
            }
        });

        this.socket.on(Socket.EVENT_CONNECT.toString(), new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Map<String, String> authData = new HashMap<>();
                authData.put("projectId", opts.getProjectId());
                authData.put("token", opts.getToken());
                System.out.println("EVENT_CONNECT ");
                // handshake for authentication purpose
                socket.emit(BizEvent.AUTH.toString(), authData, new Ack() {

                    @Override
                    public void call(Object... authCode) {
                        System.out.println("Handshake status " + authCode);

                        AuthCode code = AuthCode.from((String) authCode[0]);
                        // failed to auth, disconnect and won't retry
                        if (AuthCode.AUTH_FAILED.equals(code)) {
                            onConnected.onFinished(code.toString());
                            disconnect();
                            return;
                        }
                        changeState(ClientState.CONNECTED);
                        onConnected.onFinished("");
                        startProcess();
                    }
                });
            }
        });

        socket.connect();
    }

    private Options getSocketOptions() {
        Options socketOptions = new Options();
        socketOptions.multiplex = false;
        socketOptions.reconnection = this.opts.getReconnect().getReconnection();
        socketOptions.reconnectionAttempts = this.opts.getReconnect().getReconnectionAttempts();
        socketOptions.reconnectionDelay = this.opts.getReconnect().getReconnectionDelay();
        socketOptions.reconnectionDelayMax = this.opts.getReconnect().getReconnectionDelayMax();
        return socketOptions;
    }

    public void disconnect() {
        try {
            changeState(ClientState.DISCONNECTED);

            stateChangeCallbacks = new ArrayList<StateChangeCallback>();
            events = new ArrayList<EventStruct>();

            Socket s = this.socket;
            socket = null;

            s.close();
        } catch (Exception error) {
            System.err.println(error.getMessage());
        }
    }

    public void onStateChange(StateChangeCallback cb) {
        if (cb == null) {
            return;
        }

        if (!this.stateChangeCallbacks.contains(cb)) {
            this.stateChangeCallbacks.add(cb);
        }
    }

    private void changeState(final ClientState state) {
        this.stateChangeCallbacks.forEach(new Consumer<StateChangeCallback>() {
            public void accept(StateChangeCallback cb) {
                cb.onChange(state);
            }
        });
    }

    public void subscribe(String topic, String event, EventCallback callback) {
        if (topic == null || event == null || callback == null) {
            throw new RuntimeException("topic or event or callback cannot be empty");
        }

        events.add(new EventStruct(topic, event, callback));
    }

    private void startProcess() {
        List<String> topics = new ArrayList<>();
        for (EventStruct e : events) {
            topics.add(e.getEvent());
        }

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
                        socket.on(e.getEvent(), new Emitter.Listener() {

                            @Override
                            public void call(Object... args) {
                                JSONObject obj = (JSONObject) args[0];
                                EventMessage message;
                                try {
                                    message = new EventMessage(obj.getString("projectId"), obj.getString("topic"),
                                            obj.getString("event"), obj.getString("payload"));
                                    if (e.getTopic().equals(message.getTopic())) {
                                        e.getCallback().onFire(message);
                                    }
                                } catch (JSONException err) {
                                    System.err.println(err);
                                }

                            }
                        });
                    }
                });
            }
        });
    }

    private void endProcess() {
        this.events.forEach(new Consumer<EventStruct>() {
            public void accept(EventStruct e) {
                socket.off(e.getEvent());
            }
        });
    }

}
