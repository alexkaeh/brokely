package com.techelevator.tenmo.services;

public enum Url {
    BASE("http://localhost:8080/"),
    USER(BASE + "user/"),
    ACCOUNT(BASE + "account/"),
        BALANCE(ACCOUNT + "balance/"),
    TRANSFER(BASE + "transfer/"),
        SEND(TRANSFER + "send/"),
        PENDING(TRANSFER + "pending/");

    private final String path;

    Url(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
