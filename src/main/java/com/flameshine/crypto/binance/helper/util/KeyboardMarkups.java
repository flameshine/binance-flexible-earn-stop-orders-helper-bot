package com.flameshine.crypto.binance.helper.util;

import java.util.List;
import java.util.stream.Stream;

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

        var connect = InlineKeyboardButton.builder()
            .text("Connect")
            .callbackData("connect")
            .build();

        var myAccounts = InlineKeyboardButton.builder()
            .text("My accounts")
            .callbackData("my_accounts")
            .build();

        var disconnect = InlineKeyboardButton.builder()
            .text("Disconnect")
            .callbackData("disconnect")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(connect, myAccounts, disconnect, BACK))
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

    public static InlineKeyboardMarkup accountListMenu(List<Account> accounts) {

        var accountRows = accounts.stream()
            .map(KeyboardMarkups::toKeyboardRow)
            .toList();

        var keyboard = Stream.concat(accountRows.stream(), Stream.of(List.of(BACK)))
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