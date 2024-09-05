package com.flameshine.crypto.binance.helper.handler.message.impl;

import java.util.List;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.entity.Account;
import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
public class ApiKeyMessageHandler implements MessageHandler {

    private static final Pattern API_KEY_PATTERN = Pattern.compile("(\\S+)\\s*-\\s*(\\S+)");

    @Override
    @Transactional
    public HandlerResponse handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        var matcher = API_KEY_PATTERN.matcher(message.getText());

        if (!matcher.matches()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.API_KEY_SETUP_FAILURE)
                .build();

            return new HandlerResponse(
                List.of(sendMessage),
                UserState.WAITING_FOR_API_KEY
            );
        }

        var account = Account.builder()
            .telegramUserId(message.getFrom().getId())
            .name(matcher.group(1))
            .binanceApiKey(matcher.group(2))
            .build();

        account.persist();

        var sendMessage = sendMessageBuilder
            .text(Messages.API_KEY_SETUP_SUCCESS)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}