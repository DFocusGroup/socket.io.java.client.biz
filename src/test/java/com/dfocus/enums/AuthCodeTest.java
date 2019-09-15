package com.dfocus.enums;

import org.junit.Assert;
import org.junit.Test;

public class AuthCodeTest {
	@Test
	public void equals() {
		Assert.assertTrue(AuthCode.AUTH_SUCCESS.equals("auth_success"));
		Assert.assertTrue(AuthCode.AUTH_FAILED.equals("auth_fail"));
	}

	@Test
	public void from() {
		Assert.assertEquals(AuthCode.from("auth_success"), AuthCode.AUTH_SUCCESS);
		Assert.assertEquals(AuthCode.from("auth_fail"), AuthCode.AUTH_FAILED);
	}
}