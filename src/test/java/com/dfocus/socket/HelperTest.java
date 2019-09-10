package com.dfocus.socket;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
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
		JSONArray obj = new JSONArray();

		Map<String, String> authData = new HashMap<>();
		authData.put("projectId", "fm");
		authData.put("token", "dasfasdfa");

		obj.put("auth");
		obj.put(authData);

		System.out.println("FUCK: " + obj.toString());

		// java 环境 FUCK: ["auth",{"projectId":"fm","token":"dasfasdfa"}]
		// Android 环境 FUCK: ["auth","{projectId=fm, token=dasfasdfa}"]

	}

}