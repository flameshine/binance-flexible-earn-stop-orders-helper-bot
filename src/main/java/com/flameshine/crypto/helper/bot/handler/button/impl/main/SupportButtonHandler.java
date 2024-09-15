package com.flameshine.crypto.helper.bot.handler.button.impl.main;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

public class SupportButtonHandler implements ButtonHandler {

    @Override
    public Response handle(CallbackQuery query) {

        var sendMessage = SendMessage.builder()
            .chatId(query.getMessage().getChatId())
            .text(Messages.supportDetails())
            .parseMode(ParseMode.MARKDOWNV2)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}