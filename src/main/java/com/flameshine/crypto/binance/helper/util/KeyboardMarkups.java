package com.flameshine.crypto.binance.helper.util;

import java.util.List;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.flameshine.crypto.binance.helper.entity.Account;

// TODO: add emojis

@UtilityClass
public class KeyboardMarkups {

    private static final InlineKeyboardButton BACK = InlineKeyboardButton.builder()
        .text("Back")
        .callbackData("back")
        .build();

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

        var userAccounts = InlineKeyboardButton.builder()
            .text("Your accounts")
            .callbackData("user_accounts")
            .build();

        var connect = InlineKeyboardButton.builder()
            .text("Connect")
            .callbackData("connect")
            .build();

        var disconnect = InlineKeyboardButton.builder()
            .text("Disconnect")
            .callbackData("disconnect")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(userAccounts, connect, disconnect, BACK))
            .build();
    }

    public static InlineKeyboardMarkup orderMenu() {

        var newOrder = InlineKeyboardButton.builder()
            .text("New")
            .callbackData("new")
            .build();

        var orders = InlineKeyboardButton.builder()
            .text("Your orders")
            .callbackData("orders")
            .build();

        var cancel = InlineKeyboardButton.builder()
            .text("Cancel")
            .callbackData("cancel")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(newOrder, orders, cancel, BACK))
            .build();
    }

    public static InlineKeyboardMarkup accountList(List<Account> accounts) {

        var keyboard = accounts.stream()
            .map(KeyboardMarkups::toKeyboardRow)
            .toList();

        return InlineKeyboardMarkup.builder()
            .keyboard(keyboard)
            .build();
    }

    private static List<InlineKeyboardButton> toKeyboardRow(Account account) {

        var button =  InlineKeyboardButton.builder()
            .text(account.getName())
            .callbackData(account.getName())
            .build();

        return List.of(button);
    }
}