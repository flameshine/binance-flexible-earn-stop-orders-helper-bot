package com.flameshine.crypto.binance.helper.handler.command.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.handler.command.CommandHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

public class MainMenuCommandHandler implements CommandHandler {

    public MainMenuCommandHandler() {}

    @Override
    public HandlerResponse handle(Update update) {

        var sendMessage = SendMessage.builder()
            .chatId(update.getMessage().getFrom().getId())
            .parseMode(ParseMode.HTML)
            .replyMarkup(KeyboardMenu.MAIN.getMarkup())
            .text(Messages.MAIN_MENU)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}