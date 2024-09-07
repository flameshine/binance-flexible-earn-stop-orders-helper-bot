package com.flameshine.crypto.binance.helper.handler.button.impl.main;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.enums.MainMenuButton;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

public class MainMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler supportButtonHandler;

    public MainMenuButtonHandler() {
        this.supportButtonHandler = new SupportButtonHandler();
    }

    @Override
    public Response handle(CallbackQuery query) {

        var message = query.getMessage();

        var text = EditMessageText.builder()
            .chatId(message.getChatId())
            .messageId(message.getMessageId())
            .text("")
            .build();

        var markup = EditMessageReplyMarkup.builder()
            .chatId(message.getChatId())
            .messageId(message.getMessageId())
            .build();

        var buttonData = MainMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case ACCOUNTS -> {
                text.setText(Messages.ACCOUNT_MENU);
                markup.setReplyMarkup(KeyboardMenu.ACCOUNT.getMarkup());
            }

            case ORDERS -> {
                text.setText(Messages.ORDER_MENU);
                markup.setReplyMarkup(KeyboardMenu.ORDER.getMarkup());
            }

            case SUPPORT -> {
                return supportButtonHandler.handle(query);
            }
        }

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return new Response(
            List.of(answer, text, markup)
        );
    }
}