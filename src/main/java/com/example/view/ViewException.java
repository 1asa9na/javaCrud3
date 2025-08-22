package com.example.view;

/**
 * Custom exception for View layer errors.
 */

public class ViewException extends RuntimeException {
    public ViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewException() {
    }
}
