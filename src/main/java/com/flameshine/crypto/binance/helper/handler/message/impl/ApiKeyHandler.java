package com.flameshine.crypto.binance.helper.handler.message.impl;

import java.util.List;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.entity.Account;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

// TODO: validate API key if possible

@ApplicationScoped
public class ApiKeyHandler implements MessageHandler {

    private static final Pattern API_KEY_PATTERN = Pattern.compile("(\\S+)\\s*-\\s*(\\S+)");

    @Override
    @Transactional
    public HandlerResponse handle(Message message) {

        var matcher = API_KEY_PATTERN.matcher(message.getText());

        if (matcher.matches()) {

            var account = Account.builder()
                .telegramUserId(message.getFrom().getId())
                .name(matcher.group(1))
                .binanceApiKey(matcher.group(2))
                .build();

            account.persist();

        } else {
            // TODO: handle
        }

        var sendMessage = SendMessage.builder()
            .chatId(message.getChatId())
            .text(Messages.API_KEY_SETUP_SUCCESS)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}