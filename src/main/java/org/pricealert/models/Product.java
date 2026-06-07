package org.pricealert.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "products")
public class Product {
    private final String id;
    private final String title;

    public Product(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

