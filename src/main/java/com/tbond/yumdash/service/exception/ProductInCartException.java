package com.tbond.yumdash.service.exception;

public class ProductInCartException extends RuntimeException {
    public ProductInCartException(String message) {
        super(message);
    }
}
