package com.flameshine.crypto.binance.helper.handler.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.flameshine.crypto.binance.helper.handler.UpdateHandler;
import com.flameshine.crypto.binance.helper.util.Messages;

public class StartHandler implements UpdateHandler {

    public StartHandler() {}

    @Override
    public List<BotApiMethod<?>> handle(Update update) {

        var sendMessage = SendMessage.builder()
            .chatId(update.getMessage().getFrom().getId())
            .text(Messages.INITIAL)
            .build();

        return List.of(sendMessage);
    }
}