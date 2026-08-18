package org.pricealert.scheduledTasks;


import org.pricealert.exceptions.PriceNotFoundException;
import org.pricealert.models.Offer;
import org.pricealert.repository.OfferRepository;
import org.pricealert.service.ProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ScheduledTasks {
    private final OfferRepository offerRepository;
    private final ProductService productService;

    public ScheduledTasks(OfferRepository offerRepository, ProductService productService) {
        this.offerRepository = offerRepository;
        this.productService = productService;
    }

    private List<Offer> getProducts() {
        return offerRepository.findAll();
    }

    @Scheduled(fixedRate = 21600000)
    public void printOfferTitles() throws PriceNotFoundException, IOException {
        List<Offer> offerList = getProducts();

        for (Offer offer : offerList) {
            System.out.println(offer.getUrl());
            productService.saveProduct(offer.getUrl());
        }
    }
}
