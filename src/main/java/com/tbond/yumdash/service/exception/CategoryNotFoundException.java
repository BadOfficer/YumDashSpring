package com.tbond.yumdash.service.exception;

public class CategoryNotFoundException extends RuntimeException {
    private static final String PRODUCT_NOT_FOUND = "Product with ID - %s not found";

    public CategoryNotFoundException(String id) {
        super(String.format(PRODUCT_NOT_FOUND, id));
    }
}
