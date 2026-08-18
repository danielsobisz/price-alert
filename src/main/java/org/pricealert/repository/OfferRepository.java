package org.pricealert.repository;
import java.util.Optional;
import org.pricealert.models.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer, String> {
    boolean existsByProductId(String productId);

    Optional<Offer> findByProductId(String title);
}
