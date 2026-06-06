package org.pricealert.models;

public class AmazonProduct implements Product {
    private final String title;
    private final String price;
    private final String url;

    public AmazonProduct(String title, String price, String url) {
        this.title = title;
        this.price = price;
        this.url = url;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getPrice() { return price; }

    @Override
    public String getUrl() { return url; }
}