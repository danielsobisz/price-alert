package org.price.alert.utils;

public class InputUtils {
    public static String fulfillUrl(String url) {
        if(!url.startsWith("https://") && !url.startsWith("http://")) {
            return "https://" + url;
        }

        return url;
    }
}
