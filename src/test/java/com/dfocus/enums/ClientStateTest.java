package com.dfocus.enums;

import org.junit.Assert;
import org.junit.Test;

public class ClientStateTest {
	@Test
	public void equals() {
		Assert.assertTrue(ClientState.CONNECTED.equals("connected"));
	}

	@Test
	public void from() {
		Assert.assertEquals(ClientState.from("connected"), ClientState.CONNECTED);
	}
}