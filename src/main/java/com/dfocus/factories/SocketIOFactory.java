package com.dfocus.factories;

import java.net.URISyntaxException;

import com.dfocus.socket.Helper;
import com.dfocus.socket.SocketOpts;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.IO.Options;
import io.socket.client.Socket;

public class SocketIOFactory {
	private SocketOpts opts;
    public SocketIOFactory(SocketOpts opts) {
		this.opts = opts;
    }

    public Socket get() throws URISyntaxException {
        return IO.socket(Helper.toUrl(opts.getBase(), opts.getProjectId()), getSocketOptions());
	}
	
	public JSONObject getAuthData() {
		return Helper.toJSONObject("projectId", opts.getProjectId(), "token", opts.getToken());
	}

    private Options getSocketOptions() {
		Options socketOptions = new Options();
		socketOptions.multiplex = false;
		socketOptions.reconnection = opts.getReconnect().getReconnection();
		socketOptions.reconnectionAttempts = opts.getReconnect().getReconnectionAttempts();
		socketOptions.reconnectionDelay = opts.getReconnect().getReconnectionDelay();
		socketOptions.reconnectionDelayMax = opts.getReconnect().getReconnectionDelayMax();
		socketOptions.multiplex = false;
		socketOptions.transports = new String[] { "websocket" };
		return socketOptions;
	}
}
