package com.example.service;

/**
 * Exception class for service layer errors.
 */

public class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException() {
    }
}
