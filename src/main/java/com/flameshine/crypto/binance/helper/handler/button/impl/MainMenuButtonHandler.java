package com.flameshine.crypto.binance.helper.handler.button.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.enums.MainMenuButton;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

// TODO: implement "Accounts" functionality
// TODO: implement "Orders" functionality

@ApplicationScoped
@Named("MainMenuButtonHandler")
public class MainMenuButtonHandler implements ButtonHandler {

    public MainMenuButtonHandler() {}

    @Override
    public HandlerResponse handle(CallbackQuery query) {

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

        var sendMessageBuilder = SendMessage.builder()
            .chatId(chatId);

        var buttonData = MainMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case ACCOUNTS -> {
                newText.setText(Messages.ACCOUNT_MENU);
                newMarkup.setReplyMarkup(Keyboard.ACCOUNT_MENU.getMarkup());
            }

            case ORDERS -> {
                newText.setText(Messages.ORDER_MENU);
                newMarkup.setReplyMarkup(Keyboard.ORDER_MENU.getMarkup());
            }

            case HELP -> {

                var sendMessage = sendMessageBuilder
                    .text(Messages.HELP)
                    .build();

                return new HandlerResponse(
                    List.of(sendMessage)
                );
            }

            case SUPPORT -> {

                var sendMessage = sendMessageBuilder
                    .text(Messages.SUPPORT)
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
            List.of(answer, newText, newMarkup)
        );
    }
}