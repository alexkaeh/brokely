package com.techelevator.tenmo.logic;

public enum Message {
    A("A");

    private final String msg;

    Message(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
