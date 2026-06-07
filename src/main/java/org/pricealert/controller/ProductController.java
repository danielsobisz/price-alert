package org.pricealert.controller;

import org.pricealert.models.Offer;
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

    @GetMapping("/offers")
    public Collection<Offer> get() { return productService.get();}

    @PostMapping("/offer")
    public Offer save(@RequestBody Offer offer) {
        return productService.save(offer);
    }
}