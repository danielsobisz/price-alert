package org.pricealert.repository;

import org.pricealert.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByUpc(String upc);
}
