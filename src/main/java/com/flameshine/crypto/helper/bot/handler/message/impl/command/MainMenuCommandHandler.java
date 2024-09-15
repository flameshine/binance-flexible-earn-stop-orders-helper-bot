package com.flameshine.crypto.helper.bot.handler.message.impl.command;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.bot.enums.Keyboard;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

public class MainMenuCommandHandler implements MessageHandler {

    @Override
    public Response handle(Message message) {

        var sendMessage = SendMessage.builder()
            .chatId(message.getChatId())
            .parseMode(ParseMode.HTML)
            .replyMarkup(Keyboard.MAIN.getMarkup())
            .text(Messages.MAIN_MENU)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}