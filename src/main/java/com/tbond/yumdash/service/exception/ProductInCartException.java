package com.tbond.yumdash.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductInCartException extends RuntimeException {
    public ProductInCartException(String message) {
        super(message);
    }
}
