package com.dfocus.socket;

import org.json.JSONObject;

public class Helper {

	public static String toUrl(String... paths) {
		StringBuffer sb = new StringBuffer();

		for (String path : paths) {
			sb.append(path);
			sb.append("/");
		}
		return sb.toString().replaceAll("/+", "/").replaceAll("^\\/", "").replaceAll("\\/$", "")
				.replace("http:/", "http://").replace("https:/", "https://");
	}

	public static JSONObject toJSONObject(final String... data) {

		final JSONObject obj = new JSONObject();

		for (int i = 0; i < data.length; i = i + 2) {
			try {
				obj.put(data[i], data[i + 1]);
			}
			catch (Exception e) {
				System.out.println(data[i + 1] + " is invalid value for " + data[i]);
			}
		}

		return obj;
	}

}