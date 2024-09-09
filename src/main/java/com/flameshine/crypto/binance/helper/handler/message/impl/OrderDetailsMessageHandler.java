package com.flameshine.crypto.binance.helper.handler.message.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.entity.Account;
import com.flameshine.crypto.binance.helper.entity.StopOrder;
import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("orderDetailsMessageHandler")
public class OrderDetailsMessageHandler implements MessageHandler {

    private static final Pattern ORDER_DETAILS_PATTERN = Pattern.compile("(^\\w+):\\s([A-Z]+)/([A-Z]+)\\s-\\s(\\d+)$");

    @Override
    @Transactional
    public Response handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        var matcher = ORDER_DETAILS_PATTERN.matcher(message.getText());

        if (!matcher.matches()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.ORDER_CREATION_FAILURE)
                .build();

            return new Response(
                List.of(sendMessage),
                UserState.WAITING_FOR_ORDER_DETAILS
            );
        }

        var account = Account.findByName(matcher.group(1));

        if (account == null) {

            var sendMessage = sendMessageBuilder
                .text(Messages.UNKNOWN_ACCOUNT)
                .build();

            return new Response(
                List.of(sendMessage),
                UserState.WAITING_FOR_ORDER_DETAILS
            );
        }

        var order = StopOrder.builder()
            .base(matcher.group(2))
            .quote(matcher.group(3))
            .target(new BigDecimal(matcher.group(4)))
            .account(account)
            .build();

        order.persist();

        var sendMessage = sendMessageBuilder
            .text(Messages.ORDER_CREATION_SUCCESS)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}