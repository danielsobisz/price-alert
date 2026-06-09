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

    public Product(String title,String upc) {
        this.title = title;
        this.upc = upc;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUpc() {
        return upc;
    }
}

