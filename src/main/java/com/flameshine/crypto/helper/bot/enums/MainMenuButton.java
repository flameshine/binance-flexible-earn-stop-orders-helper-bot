package com.flameshine.crypto.helper.bot.enums;

import java.util.Arrays;

public enum MainMenuButton {

    DISCONNECT,
    ORDERS,
    HELP,
    SUPPORT;

    public static MainMenuButton fromValue(String value) {
        return Arrays.stream(MainMenuButton.values())
            .filter(mainMenuButton -> mainMenuButton.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid main menu button: " + value));
    }
}