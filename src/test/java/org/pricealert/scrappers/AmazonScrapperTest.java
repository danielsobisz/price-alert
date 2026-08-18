package org.pricealert.scrappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.pricealert.models.ScrapedProduct;
import org.pricealert.models.Source;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class AmazonScrapperTest {
   @Test
    void parseAmazonProductPage() throws Exception {
       AmazonScrapper scrapper = new AmazonScrapper();
       InputStream inputStream = getClass().getResourceAsStream("/amazon/product-page.html");

       String html = new String(inputStream.readAllBytes(),StandardCharsets.UTF_8);
       String fakeUrl = "http://amazon.fake";
       Document doc = Jsoup.parse(html);

      ScrapedProduct result = scrapper.scrapeDocument(doc, fakeUrl);

      assertEquals(fakeUrl, result.url());
      assertEquals("Instinct 3,50mm,AMOLED,Black Bezel with Charcoal Band", result.title());
      assertEquals(new BigDecimal("1419.00"), result.price());
      assertEquals("B0DSC8GLRX", result.amazonId());
      assertEquals(Source.AMAZON, result.source());
   }
}
