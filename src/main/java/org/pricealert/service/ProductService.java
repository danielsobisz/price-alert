package org.pricealert.service;

import org.pricealert.models.AmazonProduct;
import org.pricealert.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public AmazonProduct save(AmazonProduct product) {
        return repository.save(product);
    }

    public Collection<AmazonProduct> get() {
        return repository.findAll();
    }
}
