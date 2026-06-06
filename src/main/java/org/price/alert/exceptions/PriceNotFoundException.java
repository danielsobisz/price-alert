package org.price.alert.exceptions;

public class PriceNotFoundException extends Exception {
    public PriceNotFoundException(String url) {
        super("Price not found for: " + url);
    }
}
