package org.pricealert.service;

import org.pricealert.exceptions.InvalidURLException;
import org.pricealert.exceptions.PriceNotFoundException;
import org.pricealert.input.InputValidation;
import org.pricealert.models.*;
import org.pricealert.repository.OfferRepository;
import org.pricealert.repository.PriceHistoryRepository;
import org.pricealert.repository.ProductRepository;
import org.pricealert.scrappers.AmazonScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@Service
public class ProductService {
    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final AmazonScrapper amazonScrapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    public ProductService(OfferRepository offerRepository, ProductRepository productRepository, PriceHistoryRepository priceHistoryRepository) {
        this.offerRepository = offerRepository;
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
        this.amazonScrapper = new AmazonScrapper();
    }

    public ScrapedProduct saveProduct(String url) throws IOException, URISyntaxException, PriceNotFoundException {
        if (!InputValidation.isValidURL(url)) {
            throw new InvalidURLException(url);
        }

        ScrapedProduct product = amazonScrapper.scrape(url);

        if(!this.productRepository.existsByUpc(product.upc())) {
            this.productRepository.save(new Product(product.title(),product.upc()));
        }

        Optional<Offer> existingOffer =
                offerRepository.findByProductId(product.title());

        if (existingOffer.isEmpty()) {

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

        } else {

            Offer offer = existingOffer.get();

            Query query = new Query(Criteria.where("offerId").is(offer.getId()));

            Update update = new Update()
                    .push("history", new PriceEntry(product.price()));


            mongoTemplate.updateFirst(query, update, PriceHistory.class);
        }

        return product;
    }

    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    public Collection<Offer> get() {
        return offerRepository.findAll();
    }
}
