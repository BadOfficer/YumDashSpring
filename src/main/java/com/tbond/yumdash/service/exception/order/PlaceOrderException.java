package com.tbond.yumdash.service.exception.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PlaceOrderException extends RuntimeException {
    public PlaceOrderException(String message) {
        super(message);
    }
}
