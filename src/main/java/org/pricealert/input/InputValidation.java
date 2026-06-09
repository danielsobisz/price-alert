package org.pricealert.input;
import org.pricealert.utils.InputUtils;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class InputValidation {
    public static boolean isValidURL(String url) {
        try {
            URL parsed = new URL(InputUtils.fulfillUrl(url));
            String host = parsed.getHost();

            if (host == null || host.isBlank()) {
                return false;
            }

            InetAddress.getByName(host); // DNS validation

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
