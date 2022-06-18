package com.techelevator.tenmo.services;

public enum Url {
    BASE("http://localhost:8080/"),
    USER(BASE + "user/"),
    ACCOUNT(BASE + "account/"),
    TRANSFER(BASE + "transfer/");

    private final String path;

    Url(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
