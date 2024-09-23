package com.flameshine.crypto.helper.bot.handler.message.impl.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.api.enums.OrderType;
import com.flameshine.crypto.helper.api.mapper.PriceAlertMapper;
import com.flameshine.crypto.helper.binance.alert.PriceAlertHandler;
import com.flameshine.crypto.helper.bot.enums.UserState;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("orderDetailsMessageHandler")
public class OrderDetailsMessageHandler implements MessageHandler {

    private static final Pattern ORDER_DETAILS_PATTERN = Pattern.compile("(^[bs]):\\s(\\d+\\.?\\d*)\\s([A-Z]+)/([A-Z]+)\\s-\\s(\\d+\\.?\\d*)$");

    private final PriceAlertHandler priceAlertHandler;

    @Inject
    public OrderDetailsMessageHandler(PriceAlertHandler priceAlertHandler) {
        this.priceAlertHandler = priceAlertHandler;
    }

    @Override
    @Transactional
    public HandlerResponse handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        var matcher = ORDER_DETAILS_PATTERN.matcher(message.getText());

        if (!matcher.matches()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.ORDER_CREATION_FAILURE)
                .build();

            return new HandlerResponse(
                List.of(sendMessage),
                UserState.WAITING_FOR_ORDER_DETAILS
            );
        }

        var optionalUser = Account.findByTelegramUserIdOptional(
            message.getFrom().getId()
        );

        Preconditions.checkState(optionalUser.isPresent(), "User details should be specified at this stage");

        var order = Order.builder()
            .base(matcher.group(3))
            .quote(matcher.group(4))
            .price(new BigDecimal(matcher.group(5)))
            .quantity(new BigDecimal(matcher.group(2)))
            .type(OrderType.fromValue(matcher.group(1)))
            .account(optionalUser.get())
            .build();

        order.persist();

        priceAlertHandler.handle(
            PriceAlertMapper.map(order)
        );

        var sendMessage = sendMessageBuilder
            .text(Messages.ORDER_CREATION_SUCCESS)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}