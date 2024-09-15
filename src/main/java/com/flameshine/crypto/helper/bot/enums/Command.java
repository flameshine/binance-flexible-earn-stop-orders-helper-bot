package com.flameshine.crypto.helper.bot.enums;

import lombok.Getter;

@Getter
public enum Command {

    START("Start the bot"),
    MENU("Main menu"),
    HELP("Help");

    private final String description;

    Command(String description) {
        this.description = description;
    }

    public static Command fromValue(String value) {

        for (var item : values()) {
            if (item.toString().equals(value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid command: " + value);
    }

    @Override
    public String toString() {
        return "/" + name().toLowerCase();
    }
}