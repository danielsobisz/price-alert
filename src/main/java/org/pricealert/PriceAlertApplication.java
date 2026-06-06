package org.pricealert;

import org.pricealert.input.InputValidation;
import org.pricealert.input.UserInputHandler;
import org.pricealert.models.AmazonProduct;
import org.pricealert.repository.ProductRepository;
import org.pricealert.webscrapper.WebScrapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PriceAlertApplication implements CommandLineRunner {

    private final ProductRepository productRepository;

    public PriceAlertApplication(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

                AmazonProduct product = WebScrapper.getProductInfo(url);

                productRepository.save(product);

                System.out.println("Saved: " + product.getTitle());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}