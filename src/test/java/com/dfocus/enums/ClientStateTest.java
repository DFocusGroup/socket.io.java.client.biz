package com.dfocus.enums;

import org.junit.Assert;
import org.junit.Test;

public class ClientStateTest {
	@Test
	public void equals() {
		Assert.assertTrue(ClientState.CONNECTED.equals("connected"));
		Assert.assertTrue(ClientState.CONNECTING.equals("connecting"));
		Assert.assertTrue(ClientState.DISCONNECTED.equals("disconnected"));
	}

	@Test
	public void from() {
		Assert.assertEquals(ClientState.from("connected"), ClientState.CONNECTED);
		Assert.assertEquals(ClientState.from("connecting"), ClientState.CONNECTING);
		Assert.assertEquals(ClientState.from("disconnected"), ClientState.DISCONNECTED);
	}
}