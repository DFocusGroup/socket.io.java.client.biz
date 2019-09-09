package com.dfocus.socket;

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

}