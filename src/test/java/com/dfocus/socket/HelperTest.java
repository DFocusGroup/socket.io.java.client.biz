package com.dfocus.socket;

import org.json.JSONException;
import org.json.JSONObject;
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

	@Test
	public void json() throws JSONException {
		JSONObject obj = Helper.toJSONObject("projectId", "fm", "token", "asdfg");

		Assert.assertEquals("{\"projectId\":\"fm\",\"token\":\"asdfg\"}", obj.toString());
	}

	@Test
	public void stringTojson() throws JSONException {
		JSONObject obj = new JSONObject("{\"projectId\":\"fm\",\"token\":\"asdfg\"}");

		Assert.assertEquals("fm", obj.getString("projectId"));
	}

}