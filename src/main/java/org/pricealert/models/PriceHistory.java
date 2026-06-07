package org.pricealert.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "price_history")
public abstract class PriceHistory {
    private final String offerId;
    private final String price;
    private final String timestamp;

    public PriceHistory(String offerId,String price,String timestamp) {
        this.offerId = offerId;
        this.price  = price;
        this.timestamp = timestamp;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getPrice() {
        return price;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
