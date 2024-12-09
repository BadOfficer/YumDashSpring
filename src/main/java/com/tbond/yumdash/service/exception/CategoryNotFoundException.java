package com.tbond.yumdash.service.exception;

public class CategoryNotFoundException extends RuntimeException {
    private static final String CATEGORY_NOT_FOUND = "Category with ID - %s not found";

    public CategoryNotFoundException(String id) {
        super(String.format(CATEGORY_NOT_FOUND, id));
    }
}
