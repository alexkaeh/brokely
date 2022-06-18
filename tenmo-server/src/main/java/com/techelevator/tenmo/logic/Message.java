package com.techelevator.tenmo.logic;

public enum Message {
    SENT_TO_SELF("Error: can't send money to self."),
    REQUEST_FROM_SELF("Error: can't request money from self."),
    INVALID_AMOUNT("Error: amount must be greater than zero."),
    NOT_ENOUGH_MONEY("Error: You don't have enough money to complete the request.");


    private final String msg;

    Message(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}