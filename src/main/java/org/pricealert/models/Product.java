package org.pricealert.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private final String title;
    @Indexed(unique = true)
    private final String upc;
    private final String imageUrl;

    public Product(String title,String upc,String imageUrl) {
        this.title = title;
        this.upc = upc;
        this.imageUrl = imageUrl;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() { return imageUrl;}

    public String getUpc() {
        return upc;
    }
}

