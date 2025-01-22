package com.tbond.yumdash.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
