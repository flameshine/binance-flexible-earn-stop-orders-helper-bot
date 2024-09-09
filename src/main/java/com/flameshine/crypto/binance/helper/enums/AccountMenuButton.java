package com.flameshine.crypto.binance.helper.enums;

public enum AccountMenuButton {

    USER_ACCOUNTS,
    CONNECT,
    DISCONNECT,
    BACK;

    public static AccountMenuButton fromValue(String value) {

        for (var item : values()) {
            if (item.name().equalsIgnoreCase(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid account menu button: " + value);
    }
}