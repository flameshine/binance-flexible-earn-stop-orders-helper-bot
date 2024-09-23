package com.flameshine.crypto.helper.bot.enums;

import java.util.Arrays;

public enum OrderMenuButton {

    NEW,
    USER_ORDERS,
    CANCEL,
    BACK;

    public static OrderMenuButton fromValue(String value) {
        return Arrays.stream(OrderMenuButton.values())
            .filter(orderMenuButton -> orderMenuButton.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid order menu button: " + value));
    }
}