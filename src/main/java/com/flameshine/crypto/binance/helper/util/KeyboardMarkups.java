package com.flameshine.crypto.binance.helper.util;

import java.util.List;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.flameshine.crypto.binance.helper.entity.Key;

// TODO: add emojis

@UtilityClass
public class KeyboardMarkups {

    public static final String KEY_REMOVE_PREFIX = "remove#";

    private static final InlineKeyboardButton BACK = InlineKeyboardButton.builder()
        .text("Back")
        .callbackData("back")
        .build();

    public static InlineKeyboardMarkup mainMenu() {

        var keys = InlineKeyboardButton.builder()
            .text("API Keys")
            .callbackData("keys")
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
            .keyboardRow(List.of(keys, orders, help, support))
            .build();
    }

    public static InlineKeyboardMarkup keyMenu() {

        var userKeys = InlineKeyboardButton.builder()
            .text("Your keys")
            .callbackData("user_keys")
            .build();

        var add = InlineKeyboardButton.builder()
            .text("Add")
            .callbackData("add")
            .build();

        var remove = InlineKeyboardButton.builder()
            .text("Remove")
            .callbackData("remove")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(userKeys, add, remove, BACK))
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

    public static InlineKeyboardMarkup keyList(List<Key> keys, boolean appendRemovalPrefix) {

        var keyButtons = keys.stream()
            .map(key -> toKeyboardRow(key, appendRemovalPrefix))
            .toList();

        // TODO: fix the "back" button (should return to the previous menu)

        var keyboard = Stream.of(keyButtons, List.of(BACK))
            .toList();

        return InlineKeyboardMarkup.builder()
            .keyboard(keyboard)
            .build();
    }

    private static InlineKeyboardButton toKeyboardRow(Key key, boolean appendRemovalPrefix) {
        var callbackData = appendRemovalPrefix ? KEY_REMOVE_PREFIX + key.getLabel() : key.getLabel();
        return InlineKeyboardButton.builder()
            .text(key.getLabel())
            .callbackData(callbackData)
            .build();
    }
}