package com.dfocus.socket;

public class Helper {

    public static String toUrl(String ...paths) {
       return String.join("/", paths).replaceAll("/+", "/").replaceAll("^\\/", "").replaceAll("\\/$", "").replace("http:/", "http://")
       .replace("https:/", "https://");
    }

}