package com.flameshine.crypto.binance.helper.enums;

public enum MainMenuButtonData {

    ACCOUNTS,
    ORDERS,
    HELP,
    SUPPORT;

    public static MainMenuButtonData fromValue(String value) {

        for (var item : values()) {
            if (item.name().equalsIgnoreCase(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid menu button data: " + value);
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}