package org.pricealert.scrappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pricealert.models.ProductScrapper;
import org.pricealert.utils.InputUtils;

import java.io.IOException;

public abstract class AbstractProductScrapper implements ProductScrapper {
    protected Document getDocument(String url) throws IOException {
        return Jsoup.connect(InputUtils.fulfillUrl(url)).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .header("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .referrer("https://www.google.com")
                .timeout(10000)
                .followRedirects(true)
                .get();
    }
}
