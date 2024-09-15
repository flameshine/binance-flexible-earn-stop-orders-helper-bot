package com.flameshine.crypto.helper.bot.enums;

public enum OrderMenuButton {

    NEW,
    USER_ORDERS,
    CANCEL,
    BACK;

    public static OrderMenuButton fromValue(String value) {

        for (var item : values()) {
            if (item.name().equalsIgnoreCase(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid order menu button: " + value);
    }
}