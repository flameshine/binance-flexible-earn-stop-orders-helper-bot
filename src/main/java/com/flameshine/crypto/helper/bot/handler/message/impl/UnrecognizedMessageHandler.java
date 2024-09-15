package com.flameshine.crypto.helper.bot.handler.message.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

public class UnrecognizedMessageHandler implements MessageHandler {

    @Override
    public Response handle(Message message) {

        var sendMessage = SendMessage.builder()
            .chatId(message.getChatId())
            .text(Messages.UNRECOGNIZED_MESSAGE)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}