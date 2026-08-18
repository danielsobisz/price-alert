package org.pricealert.models;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

public class PriceEntry {
    private final BigDecimal price;
    private final Date timestamp;

    public PriceEntry(BigDecimal price) {
        this.price = price;
        this.timestamp = new Date();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
