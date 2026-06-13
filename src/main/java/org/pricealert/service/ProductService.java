package org.pricealert.service;

import org.pricealert.exceptions.InvalidURLException;
import org.pricealert.exceptions.PriceNotFoundException;
import org.pricealert.input.InputValidation;
import org.pricealert.models.*;
import org.pricealert.repository.OfferRepository;
import org.pricealert.repository.PriceHistoryRepository;
import org.pricealert.repository.ProductRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
public class ProductService {
    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final ProductScrapper scrapper;
    private final MongoTemplate mongoTemplate;

    public ProductService(OfferRepository offerRepository, ProductRepository productRepository, PriceHistoryRepository priceHistoryRepository,ProductScrapper scrapper, MongoTemplate mongoTemplate) {
        this.offerRepository = offerRepository;
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
        this.scrapper = scrapper;
        this.mongoTemplate = mongoTemplate;
    }

    public ScrapedProduct saveProduct(String url) throws IOException, PriceNotFoundException {
        ScrapedProduct product = validateAndScrape(url);
        ensureProductExist(product);

        Optional<Offer> existingOffer =
                offerRepository.findByProductId(product.title());

        if (existingOffer.isEmpty()) {
            createOfferWithHistory(product);
        } else {
            appendPrice(existingOffer.get(), product);
        }

        return product;
    }

    private ScrapedProduct validateAndScrape(String url) throws IOException, PriceNotFoundException {
        if (!InputValidation.isValidURL(url)) {
            throw new InvalidURLException(url);
        }

        return scrapper.scrape(url);
    }

    private void ensureProductExist(ScrapedProduct product) {
        if(!this.productRepository.existsByUpc(product.upc())) {
            this.productRepository.save(new Product(product.title(),product.upc()));
        }
    }

    private void createOfferWithHistory(ScrapedProduct product) {
        Offer offer = offerRepository.save(
                new Offer(
                        product.title(),
                        Source.AMAZON,
                        product.url(),
                        product.price()
                )
        );

        PriceHistory history = new PriceHistory(offer.getId());
        history.addPrice(product.price());

        priceHistoryRepository.save(history);
    }

    private void appendPrice(Offer offer, ScrapedProduct product) {
        Query query = new Query(Criteria.where("offerId").is(offer.getId()));

        Update update = new Update()
                .push("history", new PriceEntry(product.price()));


        mongoTemplate.updateFirst(query, update, PriceHistory.class);
    }

    public Collection<Offer> get() {
        return offerRepository.findAll();
    }
}
