package org.pricealert.exceptions;

import org.pricealert.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ProductNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("dupa"));
    }

    @ExceptionHandler(InvalidURLException.class)
    public ResponseEntity<?> handleInvalidUrl(InvalidURLException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "error", "INVALID_URL",
                        "message", ex.getMessage()
                ));
    }
}