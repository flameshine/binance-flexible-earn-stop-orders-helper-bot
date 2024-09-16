package com.flameshine.crypto.helper.bot.handler.button.impl.order;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.enums.UserState;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

class NewButtonHandler implements ButtonHandler {

    @Override
    public Response handle(CallbackQuery query) {

        // TODO: check if user has key connected

        var sendMessage = SendMessage.builder()
            .chatId(query.getMessage().getChatId())
            .text(Messages.orderCreationTradingPair())
            .parseMode(ParseMode.MARKDOWNV2)
            .build();

        return new Response(
            List.of(sendMessage),
            UserState.WAITING_FOR_ORDER_DETAILS
        );
    }
}