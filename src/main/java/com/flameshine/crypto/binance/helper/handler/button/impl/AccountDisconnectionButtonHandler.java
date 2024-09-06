package com.flameshine.crypto.binance.helper.handler.button.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

public class AccountDisconnectionButtonHandler implements ButtonHandler {

    @Override
    public HandlerResponse handle(CallbackQuery query) {

        var sendMessage = SendMessage.builder()
            .chatId(query.getMessage().getChatId())
            .text(Messages.ACCOUNT_DISCONNECTION)
            .build();

        return new HandlerResponse(
            List.of(sendMessage),
            UserState.WAITING_FOR_ACCOUNT_TO_DISCONNECT
        );
    }
}