package com.flameshine.crypto.binance.helper.enums;

import java.util.Collection;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.flameshine.crypto.binance.helper.util.KeyboardMarkups;

@Getter
public enum KeyboardMenu {

    MAIN(KeyboardMarkups.mainMenu()),
    ACCOUNT(KeyboardMarkups.accountMenu()),
    ORDER(KeyboardMarkups.orderMenu());

    private final InlineKeyboardMarkup markup;

    KeyboardMenu(InlineKeyboardMarkup markup) {
        this.markup = markup;
    }

    public static KeyboardMenu fromButtonData(String data) {

        for (var value : values()) {

            var callbacks = value.getMarkup().getKeyboard().stream()
                .flatMap(Collection::stream)
                .map(InlineKeyboardButton::getCallbackData)
                .toList();

            if (callbacks.contains(data)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid button data: " + data);
    }
}