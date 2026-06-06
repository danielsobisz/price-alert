package org.pricealert.controller;

import org.pricealert.models.AmazonProduct;
import org.pricealert.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Collection<AmazonProduct> get() { return productService.get();}

    @PostMapping("/products")
    public AmazonProduct save(@RequestBody AmazonProduct product) {
        return productService.save(product);
    }
}