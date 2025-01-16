package com.tbond.yumdash.service.exception;

public class VerificationTokenValidationException extends RuntimeException {
    public VerificationTokenValidationException(String message) {
        super(message);
    }
}
