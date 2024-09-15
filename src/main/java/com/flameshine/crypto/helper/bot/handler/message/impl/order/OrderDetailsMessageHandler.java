package com.flameshine.crypto.helper.bot.handler.message.impl.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.api.mapper.OrderMapper;
import com.flameshine.crypto.helper.binance.BinanceOrderPriceStreamer;
import com.flameshine.crypto.helper.bot.entity.Key;
import com.flameshine.crypto.helper.bot.entity.Order;
import com.flameshine.crypto.helper.bot.enums.UserState;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("orderDetailsMessageHandler")
public class OrderDetailsMessageHandler implements MessageHandler {

    private static final Pattern ORDER_DETAILS_PATTERN = Pattern.compile("(^\\w+):\\s([A-Z]+)/([A-Z]+)\\s-\\s(\\d+)$");

    private final BinanceOrderPriceStreamer binanceOrderPriceStreamer;

    @Inject
    public OrderDetailsMessageHandler(BinanceOrderPriceStreamer binanceOrderPriceStreamer) {
        this.binanceOrderPriceStreamer = binanceOrderPriceStreamer;
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

        var key = Key.findByLabel(matcher.group(1));

        if (key == null) {

            var sendMessage = sendMessageBuilder
                .text(Messages.UNRECOGNIZED_KEY)
                .build();

            return new Response(
                List.of(sendMessage),
                UserState.WAITING_FOR_ORDER_DETAILS
            );
        }

        var order = Order.builder()
            .base(matcher.group(2))
            .quote(matcher.group(3))
            .target(new BigDecimal(matcher.group(4)))
            .key(key)
            .build();

        order.persist();

        binanceOrderPriceStreamer.monitorOrder(
            OrderMapper.map(order)
        );

        var sendMessage = sendMessageBuilder
            .text(Messages.ORDER_CREATION_SUCCESS)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}