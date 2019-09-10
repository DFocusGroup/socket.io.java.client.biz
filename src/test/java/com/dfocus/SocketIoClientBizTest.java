package com.dfocus;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.dfocus.socket.AuthCode;
import com.dfocus.socket.Finish;
import com.dfocus.socket.SocketOpts;

import org.junit.Assert;
import org.junit.Test;

public class SocketIoClientBizTest {

	@Test
	public void connectWithInvalidToken() throws URISyntaxException {

		final Map<String, String> local = new HashMap<String, String>();

		SocketOpts opts = new SocketOpts("http://139.217.99.53:9095", "fm", "invalidToken");
		SocketIoClientBiz biz = new SocketIoClientBiz(opts);

		biz.connect(new Finish() {
			@Override
			public void onFinished(String msg) {
				local.put("code", msg);
			}
		});

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(AuthCode.AUTH_FAILED.toString(), local.get("code"));
	}

	@Test
	public void connectWithValidToken() throws URISyntaxException {

		final Map<String, String> local = new HashMap<String, String>();

		SocketOpts opts = new SocketOpts("http://139.217.99.53:9095", "fm",
				"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3QiLCJleHAiOjE1NzAzMjk0MzIsImlhdCI6MTU2NzczNzQzMn0.IxyYPdM1nsAkIN8AsZCu07BiuwryhXr-lIQ_o8fNktrZZvzl7Dgldfb6DMW7reooZs6mDCAGv78_SqiUfBQtDogJOmfoQpqEuHDlG0cGvwT_oGKc6ZiYP-vipHlHP5FXBRwSwymLmF73c7HabC4IPL_4EjfZAzqX0hzeudAJRYs");
		SocketIoClientBiz biz = new SocketIoClientBiz(opts);

		biz.connect(new Finish() {
			@Override
			public void onFinished(String msg) {
				local.put("code", msg);
			}
		});

		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assert.assertEquals("", local.get("code"));
	}

}
