package org.pricealert;

import org.pricealert.input.InputValidation;
import org.pricealert.input.UserInputHandler;
import org.pricealert.models.Offer;
import org.pricealert.repository.OfferRepository;
import org.pricealert.webscrapper.WebScrapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PriceAlertApplication implements CommandLineRunner {

    private final OfferRepository offerRepository;

    public PriceAlertApplication(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PriceAlertApplication.class, args);
    }

    @Override
    public void run(String... args) {

        while (true) {
            try {
                String url = UserInputHandler.getUrl();

                if (!InputValidation.isValidURL(url)) {
                    System.out.println("URL is invalid.");
                    continue;
                }

                Offer product = WebScrapper.getProductInfo(url);

                offerRepository.save(product);

//                System.out.println("Saved: " + product.getTitle());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}