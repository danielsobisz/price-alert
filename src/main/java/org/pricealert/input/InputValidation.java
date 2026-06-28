package org.pricealert.input;

import java.net.URI;
import java.net.URISyntaxException;

public class InputValidation {
    public static boolean isValidURL(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            URI parsed = new URI(url);
            return parsed.getScheme() != null && parsed.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
