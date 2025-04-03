package com.EmailVerfication.EmaiService.Dto;

public enum EmailStatus {
    SUCCESS(1),
    FAILURE(0);
    private int value;
    EmailStatus(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
