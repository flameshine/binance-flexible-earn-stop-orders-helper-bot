package com.flameshine.crypto.binance.helper.enums;

public enum Command {

    START,
    MENU;

    public static Command fromValue(String value) {

        for (var item : values()) {
            if (item.toString().equals(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid command: " + value);
    }

    @Override
    public String toString() {
        return "/" + name().toLowerCase();
    }
}