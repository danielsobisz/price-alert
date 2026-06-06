package org.price.alert;
import org.price.alert.exceptions.PriceNotFoundException;
import org.price.alert.models.Product;
import org.price.alert.web.scrapper.WebScrapper;
import org.price.alert.input.UserInputHandler;
import org.price.alert.input.InputValidation;
import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    static void main() {
        while (true) {
            System.out.println("Hello my friend! Welcome to the web scrapper for poor people.");

            try {
                String url = UserInputHandler.getUrl();

                if (!InputValidation.isValidURL(url)) {
                    System.out.println("URL is invalid, please try again.");
                    continue;
                }

                Product product = WebScrapper.getProductInfo(url);
                System.out.println(product.getTitle());

            } catch (PriceNotFoundException e) {
                System.out.println(e.getMessage());  // prints message, loop continues
            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
            } catch (URISyntaxException e) {
                System.out.println("Invalid URL syntax: " + e.getMessage());
            }
        }
    }
}
