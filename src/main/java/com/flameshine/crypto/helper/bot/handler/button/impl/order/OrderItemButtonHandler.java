package com.flameshine.crypto.helper.bot.handler.button.impl.order;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.binance.alert.PriceAlertHandler;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("orderItemButtonHandler")
public class OrderItemButtonHandler implements ButtonHandler {

    private final PriceAlertHandler priceAlertHandler;

    @Inject
    public OrderItemButtonHandler(PriceAlertHandler priceAlertHandler) {
        this.priceAlertHandler = priceAlertHandler;
    }

    @Override
    @Transactional
    public HandlerResponse handle(CallbackQuery query) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(query.getMessage().getChatId());

        var order = Order.findByIdOptional(
            extractKeyId(query.getData())
        );

        order.ifPresent(o -> {
            o.delete();
            priceAlertHandler.remove(o.id);
        });

        var sendMessage = sendMessageBuilder
            .text(Messages.ORDER_CANCELLATION_SUCCESS)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }

    private static Long extractKeyId(String input) {
        try {
            return Long.parseLong(input.split("#")[1]);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(e);
        }
    }
}