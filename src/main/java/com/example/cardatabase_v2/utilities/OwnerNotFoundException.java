package com.example.cardatabase_v2.utilities;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(Long id) {
        super("Owner not found with id: " + id);
    }
}
