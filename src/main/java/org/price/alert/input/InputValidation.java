package org.price.alert.input;
import org.price.alert.utils.InputUtils;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class InputValidation {
    public static boolean isValidURL(String url) throws MalformedURLException, URISyntaxException {
        try {
            new URL(InputUtils.fulfillUrl(url)).toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
