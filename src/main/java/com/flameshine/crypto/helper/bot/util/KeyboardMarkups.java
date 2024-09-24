package com.flameshine.crypto.helper.bot.util;

import java.util.List;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.flameshine.crypto.helper.api.entity.Order;

@UtilityClass
public class KeyboardMarkups {

    public static final String ORDER_REMOVE_PREFIX = "order-to-remove#";

    private static final InlineKeyboardButton BACK = InlineKeyboardButton.builder()
        .text("Back")
        .callbackData("back")
        .build();

    public static InlineKeyboardMarkup mainMenu() {

        var orders = InlineKeyboardButton.builder()
            .text("\uD83D\uDCDC Orders")
            .callbackData("orders")
            .build();

        var support = InlineKeyboardButton.builder()
            .text("\uD83D\uDCB0 Support")
            .callbackData("support")
            .build();

        var disconnect = InlineKeyboardButton.builder()
            .text("\u274C Disconnect")
            .callbackData("disconnect")
            .build();

        return InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(orders, support, disconnect))
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

    public static InlineKeyboardMarkup orderList(List<Order> orders, boolean appendRemovalPrefix) {

        var orderButtons = orders.stream()
            .map(order -> toKeyboardRow(order, appendRemovalPrefix))
            .toList();

        var keyboard = Stream.concat(orderButtons.stream(), Stream.of(List.of(BACK)))
            .toList();

        return InlineKeyboardMarkup.builder()
            .keyboard(keyboard)
            .build();
    }

    private static List<InlineKeyboardButton> toKeyboardRow(Order order, boolean appendItemId) {

        var label = String.format(
            "%s: %s %s/%s - %s",
            order.getType(),
            order.getQuantity(),
            order.getBase(),
            order.getQuote(),
            order.getPrice()
        );

        var callbackData = appendItemId
            ? ORDER_REMOVE_PREFIX + order.id
            : label;

        var button = InlineKeyboardButton.builder()
            .text(label)
            .callbackData(callbackData)
            .build();

        return List.of(button);
    }
}