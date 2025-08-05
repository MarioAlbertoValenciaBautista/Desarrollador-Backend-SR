package com.backend.backend.models;

public enum StatusEnum {
    CREATED("CREATED"),
    IN_TRANSIT("IN_TRANSIT"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
