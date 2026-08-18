package org.pricealert.models;
import org.pricealert.exceptions.PriceNotFoundException;

import java.io.IOException;

public interface ProductScrapper {
    ScrapedProduct scrape(String url) throws PriceNotFoundException, IOException;
}
