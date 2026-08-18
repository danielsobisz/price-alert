package org.pricealert.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pricealert.exceptions.GlobalExceptionHandler;
import org.pricealert.exceptions.InvalidURLException;
import org.pricealert.models.Offer;
import org.pricealert.models.ScrapedProduct;
import org.pricealert.models.Source;
import org.pricealert.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest {

    private ProductService productService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getSavedProductsReturnsOffersFromService() throws Exception {
        Offer offer = new Offer(
                "B000SQLSL6",
                Source.AMAZON,
                "https://www.amazon.pl/dp/B000SQLSL6",
                new BigDecimal("32.99")
        );

        when(productService.get()).thenReturn(List.of(offer));

        mockMvc.perform(get("/products/saved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("B000SQLSL6"))
                .andExpect(jsonPath("$[0].source").value("AMAZON"))
                .andExpect(jsonPath("$[0].url").value("https://www.amazon.pl/dp/B000SQLSL6"))
                .andExpect(jsonPath("$[0].currentPrice").value(32.99));

        verify(productService).get();
    }

    @Test
    void saveProductSendsUrlToServiceAndReturnsScrapedProduct() throws Exception {
        String url = "https://www.amazon.pl/dp/B000SQLSL6";

        ScrapedProduct scrapedProduct = new ScrapedProduct(
                "NIVEA SUN Kids SPF50",
                url,
                new BigDecimal("32.99"),
                "4005900000000",
                "B000SQLSL6",
                "https://m.media-amazon.com/images/I/product.jpg",
                Source.AMAZON
        );

        when(productService.saveProduct(url)).thenReturn(scrapedProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NIVEA SUN Kids SPF50"))
                .andExpect(jsonPath("$.url").value(url))
                .andExpect(jsonPath("$.price").value(32.99))
                .andExpect(jsonPath("$.upc").value("4005900000000"))
                .andExpect(jsonPath("$.amazonId").value("B000SQLSL6"))
                .andExpect(jsonPath("$.imageUrl").value("https://m.media-amazon.com/images/I/product.jpg"))
                .andExpect(jsonPath("$.source").value("AMAZON"));

        verify(productService).saveProduct(url);
    }

    @Test
    void saveProductReturnsBadRequestWhenUrlIsInvalid() throws Exception {
        String invalidUrl = "not-a-url";

        when(productService.saveProduct(invalidUrl)).thenThrow(new InvalidURLException(invalidUrl));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(invalidUrl))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("INVALID_URL"))
                .andExpect(jsonPath("$.message").value("URL is invalid: not-a-url"));

        verify(productService).saveProduct(invalidUrl);
    }
}
