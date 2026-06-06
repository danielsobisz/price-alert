package org.pricealert.repository;

import org.pricealert.models.AmazonProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<AmazonProduct, String> {
}
