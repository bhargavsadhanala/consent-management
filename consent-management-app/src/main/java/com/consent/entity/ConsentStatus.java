package com.consent.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ConsentStatus {
    GRANTED, REVOKED;

    @JsonCreator
    public static ConsentStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status :" + value));
    }

}