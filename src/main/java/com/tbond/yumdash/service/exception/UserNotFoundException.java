package com.tbond.yumdash.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND = "User with identifier - %s not found";

    public UserNotFoundException(String identifier) {
        super(String.format(USER_NOT_FOUND, identifier));
    }
}
