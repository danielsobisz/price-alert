package org.pricealert.exceptions;

public class InvalidURLException extends RuntimeException {
    public InvalidURLException(String url) {
        super("URL is invalid: " + url);
    }
}
