package com.dfocus.factories;

import java.net.URISyntaxException;

import com.dfocus.options.SocketOpts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.socket.client.Socket;

public class SocketIOFactoryTest {

	@Test(expected = URISyntaxException.class)
	public void invalidURL() throws URISyntaxException {
		SocketOpts opts = new SocketOpts("http://mock", "^&", "111");
        SocketIOFactory factory = new SocketIOFactory(opts);
        Socket socket = factory.get();    
        
        Assert.assertNull(socket);
	}

    @Test
	public void validAuthData() throws JSONException {
		SocketOpts opts = new SocketOpts("http://mock.dfocus.com", "fm", "asdfg");
        SocketIOFactory factory = new SocketIOFactory(opts);
        JSONObject authData = factory.getAuthData();
        
        Assert.assertEquals("fm", authData.getString("projectId"));
        Assert.assertEquals("asdfg", authData.getString("token"));
	}
}