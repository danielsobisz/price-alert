package org.pricealert.repository;

import org.pricealert.models.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer, String> {

}
