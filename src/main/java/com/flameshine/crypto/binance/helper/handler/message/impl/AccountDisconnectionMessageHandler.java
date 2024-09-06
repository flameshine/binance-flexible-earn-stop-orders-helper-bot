package com.flameshine.crypto.binance.helper.handler.message.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.entity.Account;
import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("accountDisconnectionMessageHandler")
public class AccountDisconnectionMessageHandler implements MessageHandler {

    @Override
    @Transactional
    public HandlerResponse handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        var account = Account.findByName(message.getText());

        if (account == null) {

            var sendMessage = sendMessageBuilder
                .text(Messages.UNKNOWN_ACCOUNT)
                .build();

            return new HandlerResponse(
                List.of(sendMessage),
                UserState.WAITING_FOR_ACCOUNT_TO_DISCONNECT
            );
        }

        Account.deleteById(account.id);

        var sendMessage = sendMessageBuilder
            .text(Messages.ACCOUNT_DISCONNECTION_SUCCESS)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}