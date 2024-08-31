package com.flameshine.crypto.binance.helper.handler.message.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

public class ApiKeyNameHandler implements MessageHandler {

    @Override
    public HandlerResponse handle(Message message) {

        var sendMessage = SendMessage.builder()
            .chatId(message.getFrom().getId())
            .text(Messages.API_KEY_SETUP_SUCCESS)
            .build();

        System.out.println("API key name: " + message.getText());

        // TODO: save an API key

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}