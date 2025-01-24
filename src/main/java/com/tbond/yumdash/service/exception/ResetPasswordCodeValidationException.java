package com.tbond.yumdash.service.exception;

public class ResetPasswordCodeValidationException extends RuntimeException {
  public ResetPasswordCodeValidationException(String message) {
    super(message);
  }
}
