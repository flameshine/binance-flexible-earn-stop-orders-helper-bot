package com.flameshine.crypto.helper.bot.enums;

import java.util.Arrays;

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
        return Arrays.stream(Command.values())
            .filter(command -> command.toString().equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid command: " + value));
    }

    @Override
    public String toString() {
        return "/" + name().toLowerCase();
    }
}