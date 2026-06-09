package org.pricealert.controller;

import org.pricealert.exceptions.PriceNotFoundException;
import org.pricealert.models.Offer;
import org.pricealert.models.ScrapedProduct;
import org.pricealert.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/saved")
    public Collection<Offer> get() { return productService.get();}

    @PostMapping()
    public ScrapedProduct saveProduct(@RequestBody String url) throws PriceNotFoundException, IOException, URISyntaxException {
         return productService.saveProduct(url);
    }
}