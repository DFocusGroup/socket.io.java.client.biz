package com.dfocus.enums;

import org.junit.Assert;
import org.junit.Test;

public class SubscribeCodeTest {
	@Test
	public void equals() {
		Assert.assertTrue(SubscribeCode.SUB_SUCCESS.equals("sub_success"));
		Assert.assertTrue(SubscribeCode.SUB_FAILED.equals("sub_fail"));
	}

	@Test
	public void from() {
		Assert.assertEquals(SubscribeCode.from("sub_success"), SubscribeCode.SUB_SUCCESS);
		Assert.assertEquals(SubscribeCode.from("sub_fail"), SubscribeCode.SUB_FAILED);
	}
}