package com.flameshine.crypto.binance.helper.handler.command.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.flameshine.crypto.binance.helper.handler.command.CommandHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

public class HelpCommandHandler implements CommandHandler {

    public HelpCommandHandler() {}

    @Override
    public HandlerResponse handle(Update update) {

        var apiKeySetupMessage = SendMessage.builder()
            .chatId(update.getMessage().getFrom().getId())
            .text(Messages.HELP)
            .build();

        var methods = List.of(apiKeySetupMessage);

        return new HandlerResponse(methods);
    }
}