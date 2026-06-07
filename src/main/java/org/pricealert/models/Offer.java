package org.pricealert.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "offers")
public class Offer {
    private final String id;
    private final String productId;
    private final Source source;
    private final String url;
    private final String currentPrice;

    public Offer(String productId, Source source, String url, String currentPrice) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.url = url;
        this.source = source;
        this.currentPrice = currentPrice;
    }


    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public Source getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }
}
