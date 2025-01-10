package com.tbond.yumdash.service.exception;

public class CartItemNotFound extends RuntimeException {
    private static final String CART_ITEM_NOT_FOUND = "Cart item with ID - %s not found";

    public CartItemNotFound(String id) {
        super(String.format(CART_ITEM_NOT_FOUND, id));
    }
}
