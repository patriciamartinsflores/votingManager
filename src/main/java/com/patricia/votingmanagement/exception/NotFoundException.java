package com.patricia.votingmanagement.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(Long id, String itemName) {
        super(itemName + " with id: " + id + " not found.");
    }
}