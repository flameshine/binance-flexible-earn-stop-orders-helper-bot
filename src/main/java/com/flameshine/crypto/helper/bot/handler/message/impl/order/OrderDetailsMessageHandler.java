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

import com.flameshine.crypto.helper.api.mapper.PriceAlertMapper;
import com.flameshine.crypto.helper.binance.streamer.PriceDataStreamer;
import com.flameshine.crypto.helper.bot.entity.Key;
import com.flameshine.crypto.helper.bot.entity.Order;
import com.flameshine.crypto.helper.bot.enums.UserState;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("orderDetailsMessageHandler")
public class OrderDetailsMessageHandler implements MessageHandler {

    private static final Pattern ORDER_DETAILS_PATTERN = Pattern.compile("(^[bs]):\\s(\\d+\\.?\\d*)\\s([A-Z]+)/([A-Z]+)\\s-\\s(\\d+\\.?\\d*)$");

    private final PriceDataStreamer priceDataStreamer;

    @Inject
    public OrderDetailsMessageHandler(PriceDataStreamer priceDataStreamer) {
        this.priceDataStreamer = priceDataStreamer;
    }

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

        var optionalKey = Key.findByTelegramUserIdOptional(
            message.getFrom().getId()
        );

        Preconditions.checkState(optionalKey.isPresent(), "An API key should be connected at this stage");

        var order = Order.builder()
            .base(matcher.group(3))
            .quote(matcher.group(4))
            .price(new BigDecimal(matcher.group(5)))
            .amount(new BigDecimal(matcher.group(2)))
            .type(Order.Type.fromValue(matcher.group(1)))
            .key(optionalKey.get())
            .build();

        order.persist();

        priceDataStreamer.stream(
            PriceAlertMapper.map(order)
        );

        var sendMessage = sendMessageBuilder
            .text(Messages.ORDER_CREATION_SUCCESS)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}