package com.techelevator.tenmo.services;

public enum Path {
    USER("user/"),
    ACCOUNT("account/"),
    TRANSFER("transfer/");

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
