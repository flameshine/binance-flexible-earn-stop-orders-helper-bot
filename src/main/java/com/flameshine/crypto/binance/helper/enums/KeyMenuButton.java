package com.flameshine.crypto.binance.helper.enums;

public enum KeyMenuButton {

    USER_KEYS,
    ADD,
    REMOVE,
    BACK;

    public static KeyMenuButton fromValue(String value) {

        for (var item : values()) {
            if (item.name().equalsIgnoreCase(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid key menu button: " + value);
    }
}