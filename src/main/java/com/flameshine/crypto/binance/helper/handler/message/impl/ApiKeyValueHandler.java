package com.flameshine.crypto.binance.helper.handler.message.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

// TODO: validate API key if possible

public class ApiKeyValueHandler implements MessageHandler {

    @Override
    public HandlerResponse handle(Message message) {

        var sendMessage = SendMessage.builder()
            .chatId(message.getFrom().getId())
            .text(Messages.API_KEY_NAME)
            .build();

        System.out.println("API key value: " + message.getText());

        // TODO: handle API key name
        // TODO: save an API key

        return new HandlerResponse(
            List.of(sendMessage),
            UserState.WAITING_FOR_API_KEY_NAME
        );
    }
}