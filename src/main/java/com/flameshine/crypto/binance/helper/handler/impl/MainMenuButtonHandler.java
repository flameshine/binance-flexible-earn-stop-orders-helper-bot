package com.flameshine.crypto.binance.helper.handler.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.MainMenuButtonData;
import com.flameshine.crypto.binance.helper.handler.ButtonHandler;
import com.flameshine.crypto.binance.helper.util.KeyboardMarkups;
import com.flameshine.crypto.binance.helper.util.Messages;

public class MainMenuButtonHandler implements ButtonHandler {

    public MainMenuButtonHandler() {}

    @Override
    public List<BotApiMethod<?>> handle(CallbackQuery query) {

        var message = query.getMessage();
        var chatId = message.getChatId();

        var newText = EditMessageText.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .text(Messages.MAIN_MENU)
            .build();

        var newMarkup = EditMessageReplyMarkup.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .build();

        var buttonData = MainMenuButtonData.fromValue(query.getData());

        switch (buttonData) {

            case ACCOUNTS -> {
                newText.setText(Messages.ACCOUNT_MENU);
                newMarkup.setReplyMarkup(KeyboardMarkups.accountMenu());
            }

            case ORDERS -> {
                newText.setText(Messages.ORDER_MENU);
                newMarkup.setReplyMarkup(KeyboardMarkups.orderMenu());
            }

            case HELP -> {
                var sendMessage =  SendMessage.builder()
                    .chatId(chatId)
                    .text(Messages.HELP)
                    .build();
                return List.of(sendMessage);
            }

            case SUPPORT -> {
                var sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(Messages.SUPPORT)
                    .build();
                return List.of(sendMessage);
            }
        }

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return List.of(answer, newText, newMarkup);
    }
}