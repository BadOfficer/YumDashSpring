package com.tbond.yumdash.service.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND = "Product with ID - %s not found";

    public UserNotFoundException(String id) {
        super(String.format(USER_NOT_FOUND, id));
    }
}
