package com.flameshine.crypto.helper.bot.enums;

public enum MainMenuButton {

    DISCONNECT,
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