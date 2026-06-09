package org.pricealert.repository;

import org.pricealert.models.Offer;
import org.pricealert.models.PriceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PriceHistoryRepository extends MongoRepository<PriceHistory, String> {
    Optional<PriceHistory> findByOfferId(String offerId);
}
