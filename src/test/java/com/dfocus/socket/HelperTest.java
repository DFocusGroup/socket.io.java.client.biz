package com.dfocus.socket;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
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
	public void checkOrgJson() throws JSONException {

		Map<String, String> authData = new HashMap<String, String>();
		authData.put("token", "asdfgh");

		JSONArray arr = new JSONArray();
		arr.put("auth");
		arr.put(authData);

		System.out.println(arr.toString());

		Assert.assertEquals("[\"auth\",{\"token\":\"asdfgh\"}]", arr.toString());
		// Android env is: ["auth","{token=asdfgh}"]
	}

}