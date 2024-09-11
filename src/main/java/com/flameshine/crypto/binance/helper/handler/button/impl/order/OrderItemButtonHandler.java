package com.flameshine.crypto.binance.helper.handler.button.impl.order;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.entity.Order;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("orderItemButtonHandler")
public class OrderItemButtonHandler implements ButtonHandler {

    @Override
    @Transactional
    public Response handle(CallbackQuery query) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(query.getMessage().getChatId());

        var order = Order.findByIdOptional(
            extractKeyId(query.getData())
        );

        order.ifPresent(PanacheEntityBase::delete);

        var sendMessage = sendMessageBuilder
            .text(Messages.ORDER_CANCELLATION_SUCCESS)
            .build();

        return new Response(
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