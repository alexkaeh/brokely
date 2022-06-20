/**
 * This enum class contains a hierarchical list of all the urls used in the API
 */

package com.techelevator.tenmo.info;

public enum Url {
    BASE("http://localhost:8080/"),
    USER(BASE + "user/"),
    ACCOUNT(BASE + "account/"),
        BALANCE(ACCOUNT + "balance/"),
    TRANSFER(BASE + "transfer/"),
        SEND(TRANSFER + "send/"),
        REQUEST(TRANSFER + "request/"),
        PENDING(TRANSFER + "pending/"),
            APPROVE(PENDING + "approve/"),
            REJECT(PENDING + "reject/");

    private final String path;

    Url(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
