package com.flameshine.crypto.helper.bot.handler.message.impl.command;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.Messages;

public class HelpCommandHandler implements MessageHandler {

    @Override
    public HandlerResponse handle(Message message) {

        var sendMessage = SendMessage.builder()
            .chatId(message.getChatId())
            .text(Messages.HELP)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}