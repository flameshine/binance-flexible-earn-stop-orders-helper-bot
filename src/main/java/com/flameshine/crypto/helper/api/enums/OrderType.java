package com.flameshine.crypto.helper.api.enums;

public enum OrderType {

    BUY,
    SELL;

    public static OrderType fromValue(String value) {

        for (var item : values()) {
            if (item.name().substring(0, 1).equalsIgnoreCase(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid order type: " + value);
    }

    @Override
    public String toString() {
        return name().toUpperCase();
    }
}