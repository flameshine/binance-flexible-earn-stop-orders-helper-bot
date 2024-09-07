package com.flameshine.crypto.binance.helper.handler.message.impl.command;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

public class StartCommandHandler implements MessageHandler {

    @Override
    public Response handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        var greetingMessage = sendMessageBuilder
            .text(Messages.greeting())
            .parseMode(ParseMode.MARKDOWNV2)
            .build();

        // TODO: check if user already has one and handle it

        var apiKeySetupMessage = sendMessageBuilder
            .text(Messages.accountSetup())
            .build();

        return new Response(
            List.of(greetingMessage, apiKeySetupMessage),
            UserState.WAITING_FOR_API_KEY
        );
    }
}