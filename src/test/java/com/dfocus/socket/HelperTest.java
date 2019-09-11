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

		// java 环境 FUCK: ["auth",{"projectId":"fm","token":"dasfasdfa"}]
		// Android 环境 FUCK: ["auth","{projectId=fm, token=dasfasdfa}"]

		Assert.assertEquals("{\"projectId\":\"fm\",\"token\":\"asdfg\"}", obj.toString());
	}

}