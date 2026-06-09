package org.pricealert.scrappers;

import java.io.IOException;
import java.math.BigDecimal;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pricealert.exceptions.PriceNotFoundException;
import org.pricealert.models.ScrapedProduct;
import org.pricealert.models.Source;
import org.jsoup.nodes.Document;

public class AmazonScrapper extends AbstractProductScrapper {
    @Override
    public ScrapedProduct scrape(String url) throws PriceNotFoundException, IOException {
        Document doc = getDocument(url);
        String upc = "";

        Elements rows = doc.select("tr");
        Element priceElement = doc.select("span.priceToPay span.a-price-whole").first();
        Element priceFragileElement =  doc.select("span.priceToPay span.a-price-fraction").first();
        Element titleElement = doc.select("span#productTitle").first();

        for (Element row : rows) {
            Element th = row.selectFirst("th");
            Element td = row.selectFirst("td");

            if (th != null && td != null && th.text().toLowerCase().contains("upc")) {
                upc =  td.text().trim();
            }
        }

        if(priceElement == null) {
            throw new PriceNotFoundException(url);
        }

        assert priceFragileElement != null;
        BigDecimal fullPrice = new BigDecimal(priceElement.text().replace(",",".") + priceFragileElement.text());
        assert titleElement != null;
        String title = titleElement.text();


        return new ScrapedProduct(title,url, fullPrice, upc, Source.AMAZON);
    }
}
