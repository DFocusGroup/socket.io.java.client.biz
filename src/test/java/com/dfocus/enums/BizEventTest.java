package com.dfocus.enums;

import org.junit.Assert;
import org.junit.Test;

public class BizEventTest {
	@Test
	public void equals() {
		Assert.assertTrue(BizEvent.AUTH.equals("auth"));
		Assert.assertTrue(BizEvent.SUBSCRIBE.equals("subscribe"));
	}

	@Test
	public void from() {
		Assert.assertEquals(BizEvent.from("auth"), BizEvent.AUTH);
		Assert.assertEquals(BizEvent.from("subscribe"), BizEvent.SUBSCRIBE);
	}
}