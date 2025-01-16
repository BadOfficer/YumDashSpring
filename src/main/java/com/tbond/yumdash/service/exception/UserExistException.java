package com.tbond.yumdash.service.exception;

public class UserExistException extends RuntimeException {
    private static final String USER_EXIST_MESSAGE = "User with email %s is already exist!";

    public UserExistException(String email) {
        super(String.format(USER_EXIST_MESSAGE, email));
    }
}
