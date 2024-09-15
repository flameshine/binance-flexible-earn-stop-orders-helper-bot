package com.flameshine.crypto.helper.bot.enums;

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