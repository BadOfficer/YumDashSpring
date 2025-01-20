package com.tbond.yumdash.service.exception;

public class OrderNotFoundException extends RuntimeException {
    private static final String ORDER_NOT_FOUND = "Order with ID - %s not found";

    public OrderNotFoundException(String id) {
        super(String.format(ORDER_NOT_FOUND, id));
    }
}
