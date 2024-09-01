package com.flameshine.crypto.binance.helper.handler.command.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.command.CommandHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

public class StartHandler implements CommandHandler {

    public StartHandler() {}

    @Override
    public HandlerResponse handle(Update update) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(update.getMessage().getFrom().getId());

        var greetingMessage = sendMessageBuilder
            .text(Messages.greeting())
            .parseMode(ParseMode.MARKDOWNV2)
            .build();

        // TODO: check if user already has one and handle it

        var apiKeySetupMessage = sendMessageBuilder
            .text(Messages.apiKeySetup())
            .build();

        var methods = List.of(greetingMessage, apiKeySetupMessage);

        return new HandlerResponse(methods, UserState.WAITING_FOR_API_KEY);
    }
}