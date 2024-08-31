package com.flameshine.crypto.binance.helper.handler.button.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.AccountMenuButton;
import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("AccountMenuButtonHandler")
public class AccountMenuButtonHandler implements ButtonHandler {

    public AccountMenuButtonHandler() {}

    @Override
    public HandlerResponse handle(CallbackQuery query) {

        var message = query.getMessage();
        var chatId = message.getChatId();

        var newText = EditMessageText.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .text(Messages.ACCOUNT_MENU)
            .build();

        var newMarkup = EditMessageReplyMarkup.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .build();

        var sendMessageBuilder = SendMessage.builder()
            .chatId(chatId);

        var buttonData = AccountMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case CONNECT -> {

                var sendMessage = sendMessageBuilder
                    .text(Messages.CONNECT)
                    .build();

                return new HandlerResponse(
                    List.of(sendMessage)
                );
            }

            case MY_ACCOUNTS -> {

                var sendMessage = sendMessageBuilder
                    .text(Messages.MY_ACCOUNTS)
                    .build();

                return new HandlerResponse(
                    List.of(sendMessage)
                );
            }

            case DISCONNECT -> {

                var sendMessage = sendMessageBuilder
                    .text(Messages.DISCONNECT)
                    .build();

                return new HandlerResponse(
                    List.of(sendMessage)
                );
            }

            case BACK -> {
                newText.setText(Messages.MAIN_MENU);
                newMarkup.setReplyMarkup(Keyboard.MAIN_MENU.getMarkup());
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