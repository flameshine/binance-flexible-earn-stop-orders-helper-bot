package com.flameshine.crypto.binance.helper.util;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

// TODO: add emojis

public final class KeyboardMarkups {

    public static InlineKeyboardMarkup mainMenu() {

        var accounts = InlineKeyboardButton.builder()
            .text("Accounts")
            .callbackData("accounts")
            .build();

        var orders = InlineKeyboardButton.builder()
            .text("Orders")
            .callbackData("orders")
            .build();

        var help = InlineKeyboardButton.builder()
            .text("Help")
            .callbackData("help")
            .build();

        var support = InlineKeyboardButton.builder()
            .text("Support")
            .callbackData("support")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(accounts, orders, help, support))
            .build();
    }

    public static InlineKeyboardMarkup accountMenu() {

        var connect = InlineKeyboardButton.builder()
            .text("Connect")
            .callbackData("connect")
            .build();

        var show = InlineKeyboardButton.builder()
            .text("Show connected accounts")
            .callbackData("show")
            .build();

        var disconnect = InlineKeyboardButton.builder()
            .text("Disconnect")
            .callbackData("disconnect")
            .build();

        var back = InlineKeyboardButton.builder()
            .text("Back")
            .callbackData("back")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(connect, show, disconnect, back))
            .build();
    }

    public static InlineKeyboardMarkup orderMenu() {

        var newOrder = InlineKeyboardButton.builder()
            .text("New order")
            .callbackData("new")
            .build();

        var orders = InlineKeyboardButton.builder()
            .text("Active orders")
            .callbackData("orders")
            .build();

        var cancel = InlineKeyboardButton.builder()
            .text("Cancel an order")
            .callbackData("cancel")
            .build();

        var back = InlineKeyboardButton.builder()
            .text("Back")
            .callbackData("back")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(newOrder, orders, cancel, back))
            .build();
    }

    private KeyboardMarkups() {}
}