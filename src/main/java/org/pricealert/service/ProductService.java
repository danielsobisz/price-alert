package org.pricealert.service;

import org.pricealert.models.Offer;
import org.pricealert.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {
    private final OfferRepository repository;

    public ProductService(OfferRepository repository) {
        this.repository = repository;
    }

    public Offer save(Offer offer) {
        return repository.save(offer);
    }

    public Collection<Offer> get() {
        return repository.findAll();
    }
}
