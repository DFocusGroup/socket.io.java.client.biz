package com.dfocus.enums;

import org.junit.Assert;
import org.junit.Test;

public class BizEventTest {

	@Test
	public void equals() {
		Assert.assertTrue(BizEvent.AUTH.equals("auth"));
		Assert.assertTrue(BizEvent.SUBSCRIBE.equals("subscribe"));
		Assert.assertTrue(BizEvent.AUTH.equals(BizEvent.from("auth")));
	}

	@Test
	public void from() {
		Assert.assertEquals(BizEvent.AUTH, BizEvent.from("auth"));
		Assert.assertEquals(BizEvent.SUBSCRIBE, BizEvent.from("subscribe"));
		Assert.assertEquals(null, BizEvent.from("hello"));
	}

}