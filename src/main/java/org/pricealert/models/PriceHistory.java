package org.pricealert.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "price_history")
public class PriceHistory {

    @Id
    private String id;

    private String offerId;

    private  List<PriceEntry> history = new ArrayList<>();

    public PriceHistory() {}

    public PriceHistory(String offerId) {
        this.offerId = offerId;
    }

    public void addPrice(BigDecimal price) {
        this.history.add(new PriceEntry(price));
    }

    public List<PriceEntry> getHistory() {
        return history;
    }

    public String getOfferId() {
        return offerId;
    }
}