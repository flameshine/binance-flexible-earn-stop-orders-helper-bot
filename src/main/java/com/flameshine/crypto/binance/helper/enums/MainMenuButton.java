package com.flameshine.crypto.binance.helper.enums;

public enum MainMenuButton {

    ACCOUNTS,
    ORDERS,
    HELP,
    SUPPORT;

    public static MainMenuButton fromValue(String value) {

        for (var item : values()) {
            if (item.name().equalsIgnoreCase(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid main menu button: " + value);
    }
}