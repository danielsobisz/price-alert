package org.pricealert.webscrapper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.pricealert.exceptions.PriceNotFoundException;
import org.pricealert.models.Offer;
import org.pricealert.models.Source;
import org.pricealert.utils.InputUtils;
import org.jsoup.nodes.Document;

public class WebScrapper {
    public static Document getDocument(String url) throws IOException {
        return Jsoup.connect(InputUtils.fulfillUrl(url)).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .header("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .referrer("https://www.google.com")
                .timeout(10000)
                .followRedirects(true)
                .get();
    }


    public static Offer getProductInfo(String url) throws PriceNotFoundException, IOException {
        Document doc = getDocument(url);

        Element priceElement = doc.select("span.priceToPay span.a-price-whole").first();
        Element priceFragileElement =  doc.select("span.priceToPay span.a-price-fraction").first();
        Element titleElement = doc.select("span#productTitle").first();

        if(priceElement == null) {
            throw new PriceNotFoundException(url);
        }

        assert priceFragileElement != null;
        String fullPrice = priceElement.text() + priceFragileElement.text();
        assert titleElement != null;
        String title = titleElement.text();

        return new Offer(title, Source.AMAZON,fullPrice,url);
    }
}
