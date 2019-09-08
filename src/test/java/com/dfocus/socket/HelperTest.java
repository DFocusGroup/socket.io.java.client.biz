package com.dfocus.socket;

import org.junit.Assert;
import org.junit.Test;

public class HelperTest {
	@Test
	public void toUrlWithNoSlashSections() {
		Assert.assertEquals(Helper.toUrl("http://dfocus.com", "api", "socket"), "http://dfocus.com/api/socket");
	}

	@Test
	public void toUrlWithSlashSections() {
		Assert.assertEquals(Helper.toUrl("//http://dfocus.com", "/api/", "/socket//"), "http://dfocus.com/api/socket");
	}

}