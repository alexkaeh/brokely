package com.techelevator.tenmo.services;

public enum Url {
    BASE("http://localhost:8080/"),
    USER(BASE.getPath() + "user/"),
    ACCOUNT(BASE.getPath() + "account/"),
    TRANSFER(BASE.getPath() + "transfer/");

    private final String path;

    Url(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
