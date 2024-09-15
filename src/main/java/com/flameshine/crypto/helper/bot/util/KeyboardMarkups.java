package com.flameshine.crypto.helper.bot.util;

import java.util.List;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.flameshine.crypto.helper.bot.entity.Key;
import com.flameshine.crypto.helper.bot.entity.Order;

// TODO: add emojis

@UtilityClass
public class KeyboardMarkups {

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

        var support = InlineKeyboardButton.builder()
            .text("Support")
            .callbackData("support")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(keys, orders, support))
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

        var userOrders = InlineKeyboardButton.builder()
            .text("Your orders")
            .callbackData("user_orders")
            .build();

        var newOrder = InlineKeyboardButton.builder()
            .text("New")
            .callbackData("new")
            .build();

        var cancel = InlineKeyboardButton.builder()
            .text("Cancel")
            .callbackData("cancel")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(userOrders, newOrder, cancel, BACK))
            .build();
    }

    public static InlineKeyboardMarkup keyList(List<Key> keys, boolean appendRemovalPrefix) {

        var keyButtons = keys.stream()
            .map(key -> toKeyboardRow(key, appendRemovalPrefix))
            .toList();

        // TODO: fix the "back" button (should return to the previous menu)

        var keyboard = Stream.concat(keyButtons.stream(), Stream.of(List.of(BACK)))
            .toList();

        return InlineKeyboardMarkup.builder()
            .keyboard(keyboard)
            .build();
    }

    public static InlineKeyboardMarkup orderList(List<Order> orders, boolean appendRemovalPrefix) {

        var orderButtons = orders.stream()
            .map(order -> toKeyboardRow(order, appendRemovalPrefix))
            .toList();

        // TODO: fix the "back" button (should return to the previous menu)

        var keyboard = Stream.concat(orderButtons.stream(), Stream.of(List.of(BACK)))
            .toList();

        return InlineKeyboardMarkup.builder()
            .keyboard(keyboard)
            .build();
    }

    private static List<InlineKeyboardButton> toKeyboardRow(Key key, boolean appendItemId) {

        var label = key.getLabel();

        var callbackData = appendItemId
            ? ItemRemovalPrefixes.KEY + key.id
            : label;

        var button = InlineKeyboardButton.builder()
            .text(label)
            .callbackData(callbackData)
            .build();

        return List.of(button);
    }

    private static List<InlineKeyboardButton> toKeyboardRow(Order order, boolean appendItemId) {

        var label = String.format(
            "%s: %s/%s - %s",
            order.getKey().getLabel(),
            order.getBase(),
            order.getQuote(),
            order.getTarget()
        );

        var callbackData = appendItemId
            ? ItemRemovalPrefixes.ORDER + order.id
            : label;

        var button = InlineKeyboardButton.builder()
            .text(label)
            .callbackData(callbackData)
            .build();

        return List.of(button);
    }
}