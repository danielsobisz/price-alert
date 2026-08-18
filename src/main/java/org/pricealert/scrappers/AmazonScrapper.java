package org.pricealert.scrappers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pricealert.exceptions.PriceNotFoundException;
import org.pricealert.models.ScrapedProduct;
import org.pricealert.models.Source;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class AmazonScrapper extends AbstractProductScrapper {
    private static final Pattern ASIN_PATTERN = Pattern.compile("/dp/([A-Z0-9]{10})");

    public ScrapedProduct scrapeDocument(Document doc, String url) throws PriceNotFoundException {
        String upc = "";

        Elements rows = doc.select("tr");
        Element priceElement = doc.select("span.priceToPay span.a-price-whole").first();
        Element priceFragileElement =  doc.select("span.priceToPay span.a-price-fraction").first();
        Element titleElement = doc.select("span#productTitle").first();
        Element imageElement = doc.select("img#landingImage").first();

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

        String whole = priceElement.text().replace(",", "").trim().replaceAll("\\s+","");
        String fraction = priceFragileElement != null ? priceFragileElement.text().trim() : "00";

        BigDecimal fullPrice = new BigDecimal(whole + "." + fraction);

        String title = titleElement.text();
        String amazonId = getAmazonId(doc);
        String imageUrl = getImageUrl(imageElement);

        return new ScrapedProduct(title, url, fullPrice, upc, amazonId, imageUrl, Source.AMAZON);
    }

    @Override
    public ScrapedProduct scrape(String url) throws PriceNotFoundException, IOException {
        Document doc = getDocument(url);
        return scrapeDocument(doc, url);
    }

    private String getAmazonId(Document doc) {
        Element canonicalLink = doc.select("link[rel=canonical]").first();

        if (canonicalLink != null) {
            Matcher matcher = ASIN_PATTERN.matcher(canonicalLink.attr("href"));

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        Element asinElement = doc.select("[data-csa-c-asin]").stream()
                .filter(element -> !element.attr("data-csa-c-asin").isBlank())
                .findFirst()
                .orElse(null);

        if (asinElement == null) {
            return "";
        }

        return asinElement.attr("data-csa-c-asin");
    }

    private String getImageUrl(Element imageElement) {
        if (imageElement == null) {
            return "";
        }

        String highResolutionImageUrl = imageElement.attr("data-old-hires");

        if (!highResolutionImageUrl.isBlank()) {
            return highResolutionImageUrl;
        }

        return imageElement.attr("src");
    }
}
