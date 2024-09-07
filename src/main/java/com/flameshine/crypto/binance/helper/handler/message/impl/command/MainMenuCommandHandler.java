package com.flameshine.crypto.binance.helper.handler.message.impl.command;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

public class MainMenuCommandHandler implements MessageHandler {

    @Override
    public Response handle(Message message) {

        var sendMessage = SendMessage.builder()
            .chatId(message.getChatId())
            .parseMode(ParseMode.HTML)
            .replyMarkup(KeyboardMenu.MAIN.getMarkup())
            .text(Messages.MAIN_MENU)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}