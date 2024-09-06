package com.flameshine.crypto.binance.helper.handler.button.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.enums.MainMenuButton;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

// TODO: implement "Accounts" functionality
// TODO: implement "Orders" functionality

@ApplicationScoped
public class MainMenuButtonHandler implements ButtonHandler {

    public MainMenuButtonHandler() {}

    @Override
    public HandlerResponse handle(CallbackQuery query) {

        var message = query.getMessage();
        var chatId = message.getChatId();

        var text = EditMessageText.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .text("")
            .build();

        var markup = EditMessageReplyMarkup.builder()
            .chatId(chatId)
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

                var sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(Messages.supportDetails())
                    .parseMode(ParseMode.MARKDOWNV2)
                    .build();

                return new HandlerResponse(
                    List.of(sendMessage)
                );
            }
        }

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return new HandlerResponse(
            List.of(answer, text, markup)
        );
    }
}