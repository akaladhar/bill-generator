package com.bjss.assignment.exception;

public class ItemNotFoundException extends RuntimeException {
    private String message;

    public ItemNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
