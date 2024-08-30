package com.flameshine.crypto.binance.helper.handler.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.flameshine.crypto.binance.helper.handler.UpdateHandler;
import com.flameshine.crypto.binance.helper.util.KeyboardMarkups;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("MainMenuHandler")
public class MainMenuHandler implements UpdateHandler {

    public MainMenuHandler() {}

    @Override
    public List<BotApiMethod<?>> handle(Update update) {

        var sendMessage = SendMessage.builder()
            .chatId(update.getMessage().getFrom().getId())
            .parseMode(ParseMode.HTML)
            .replyMarkup(KeyboardMarkups.mainMenu())
            .text(Messages.MAIN_MENU)
            .build();

        return List.of(sendMessage);
    }
}