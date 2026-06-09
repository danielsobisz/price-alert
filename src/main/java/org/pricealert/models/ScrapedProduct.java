package org.pricealert.models;

import java.math.BigDecimal;

public record ScrapedProduct(String title, String url, BigDecimal price, String upc, Source source) {
}
