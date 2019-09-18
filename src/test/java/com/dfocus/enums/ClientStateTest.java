package com.dfocus.enums;

import org.junit.Assert;
import org.junit.Test;

public class ClientStateTest {

	@Test
	public void equals() {
		Assert.assertTrue(ClientState.CONNECTED.equals("connected"));
		Assert.assertTrue(ClientState.CONNECTING.equals("connecting"));
		Assert.assertTrue(ClientState.DISCONNECTED.equals("disconnected"));
		Assert.assertTrue(ClientState.CONNECTED.equals(ClientState.from("connected")));
	}

	@Test
	public void from() {
		Assert.assertEquals(ClientState.CONNECTED, ClientState.from("connected"));
		Assert.assertEquals(ClientState.CONNECTING, ClientState.from("connecting"));
		Assert.assertEquals(ClientState.DISCONNECTED, ClientState.from("disconnected"));
		Assert.assertEquals(null, ClientState.from("hello"));
	}

}