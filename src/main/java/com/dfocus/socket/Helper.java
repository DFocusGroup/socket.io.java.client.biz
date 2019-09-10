package com.dfocus.socket;

import java.security.KeyStore.Entry;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONException;
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

    public static String toString(final Map<String, String> data) {

        final JSONObject obj = new JSONObject();

        data.keySet().forEach(new Consumer<String>() {
            @Override
            public void accept(String key) {
                try {
                    obj.put(key, data.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        return obj.toString();
    }
}