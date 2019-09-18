package com.dfocus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.dfocus.enums.BizEvent;
import com.dfocus.enums.ClientState;
import com.dfocus.error.InvalidArgumentException;
import com.dfocus.error.LifecycleException;
import com.dfocus.factories.SocketIOFactory;
import com.dfocus.options.SocketOpts;
import com.dfocus.socket.Finish;
import com.dfocus.socket.Helper;
import com.dfocus.socket.StateChangeCallback;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIoClientBizTest {

	final static List<String> DISCONNECT_EVENTS = Arrays.asList(Socket.EVENT_CONNECT_ERROR,
			Socket.EVENT_CONNECT_TIMEOUT, Socket.EVENT_DISCONNECT, Socket.EVENT_ERROR, Socket.EVENT_RECONNECT_ERROR,
			Socket.EVENT_RECONNECT_FAILED);

	@Test(expected = LifecycleException.class)
	public void connectCannotBeCalledMultipleTimes() throws URISyntaxException, LifecycleException {
		SocketOpts opts = new SocketOpts("http://mock.dfocus.com", "fm", "111");
		SocketIOFactory factory = new SocketIOFactory(opts);

		SocketIoClientBiz biz = new SocketIoClientBiz(factory);

		biz.connect(new Finish() {
			@Override
			public void onFinished(String errorMessage) {
			}
		});

		biz.connect(new Finish() {
			@Override
			public void onFinished(String errorMessage) {
			}
		});

	}

	@Test
	public void connectWithInvalidToken() throws URISyntaxException, LifecycleException {

		SocketIOFactory factory = mock(SocketIOFactory.class);
		Socket socket = mock(Socket.class);

		SocketIoClientBiz biz = new SocketIoClientBiz(factory);

		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Emitter.Listener callback = (Emitter.Listener) invocation.getArgument(1);
				callback.call();
				return null;
			}

		}).when(socket).on(eq(Socket.EVENT_CONNECT), any(Emitter.Listener.class));

		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Ack callback = (Ack) invocation.getArgument(2);
				callback.call("auth_fail");
				return null;
			}

		}).when(socket).emit(eq(BizEvent.AUTH.toString()), any(JSONObject.class), any(Ack.class));

		when(factory.get()).thenReturn(socket);
		when(factory.getAuthData()).thenReturn(new JSONObject());
		when(socket.connect()).thenReturn(null);

		try {
			biz.connect(new Finish() {
				@Override
				public void onFinished(String errorMessage) {
					Assert.assertEquals("auth_fail", errorMessage);
				}
			});
		}
		catch (NullPointerException e) {
			// do nothing
		}

	}

	@Test
	public void connectWithValidToken() throws URISyntaxException, LifecycleException {
		JSONObject authData = Helper.toJSONObject("projectId", "fm", "token", "validtoken");

		SocketIOFactory factory = mock(SocketIOFactory.class);
		final Socket socket = mock(Socket.class);

		SocketIoClientBiz biz = new SocketIoClientBiz(factory);

		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Emitter.Listener callback = (Emitter.Listener) invocation.getArgument(1);
				callback.call();
				return null;
			}

		}).when(socket).on(eq(Socket.EVENT_CONNECT), any(Emitter.Listener.class));

		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Ack callback = (Ack) invocation.getArgument(2);
				callback.call("auth_success");
				return null;
			}

		}).when(socket).emit(eq(BizEvent.AUTH.toString()), any(JSONObject.class), any(Ack.class));

		when(factory.get()).thenReturn(socket);
		when(factory.getAuthData()).thenReturn(authData);
		when(socket.connect()).thenReturn(null);

		biz.connect(new Finish() {
			@Override
			public void onFinished(String errorMessage) {
				Assert.assertEquals("", errorMessage);
			}
		});

		verify(socket, times(1)).on(eq(Socket.EVENT_RECONNECT), any(Emitter.Listener.class));
		verify(socket, times(1)).on(eq(Socket.EVENT_CONNECTING), any(Emitter.Listener.class));
		verify(socket, times(1)).on(eq(Socket.EVENT_RECONNECTING), any(Emitter.Listener.class));
		verify(socket, times(2)).on(eq(Socket.EVENT_ERROR), any(Emitter.Listener.class));
		verify(socket, times(1)).on(eq(Socket.EVENT_CONNECT), any(Emitter.Listener.class));
		verify(socket, times(1)).emit(eq(BizEvent.AUTH.toString()), any(JSONObject.class), any(Ack.class));
		verify(socket, times(1)).off(eq(Socket.EVENT_ERROR), any(Emitter.Listener.class));

		DISCONNECT_EVENTS.forEach(new Consumer<String>() {
			public void accept(String e) {
				if (!Socket.EVENT_ERROR.equals(e)) {
					verify(socket, times(1)).on(eq(e), any(Emitter.Listener.class));
				}
			}
		});
	}

	@Test
	public void stateChange() throws URISyntaxException, LifecycleException {
		JSONObject authData = Helper.toJSONObject("projectId", "fm", "token", "validtoken");

		SocketIOFactory factory = mock(SocketIOFactory.class);
		final Socket socket = mock(Socket.class);

		SocketIoClientBiz biz = new SocketIoClientBiz(factory);

		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Emitter.Listener callback = (Emitter.Listener) invocation.getArgument(1);
				callback.call();
				return null;
			}

		}).when(socket).on(eq(Socket.EVENT_CONNECT), any(Emitter.Listener.class));

		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Ack callback = (Ack) invocation.getArgument(2);
				callback.call("auth_success");
				return null;
			}

		}).when(socket).emit(eq(BizEvent.AUTH.toString()), any(JSONObject.class), any(Ack.class));

		when(factory.get()).thenReturn(socket);
		when(factory.getAuthData()).thenReturn(authData);
		when(socket.connect()).thenReturn(null);

		biz.onStateChange(new StateChangeCallback() {

			@Override
			public void onChange(ClientState state) {
				Assert.assertEquals(ClientState.CONNECTED, state);
			}
		});

		biz.connect(new Finish() {
			@Override
			public void onFinished(String errorMessage) {
			}
		});

	}

	@Test(expected = InvalidArgumentException.class)
	public void invalidArgument() throws InvalidArgumentException, LifecycleException {
		SocketOpts opts = new SocketOpts("http://mock.dfocus.com", "fm", "111");
		SocketIOFactory factory = new SocketIOFactory(opts);

		SocketIoClientBiz biz = new SocketIoClientBiz(factory);

		biz.subscribe(null, null, null);

	}

}
