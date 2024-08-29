package com.flameshine.crypto.binance.helper.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot {

    private final String username;

    public BinanceFlexibleEarnStopLimitsHelperBot(String token, String username) {
        super(token);
        this.username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();
        var userId = message.getFrom().getId();

        System.out.printf("User ID: %s, Content: '%s'", userId, message.getText());

        var sendMessage = SendMessage.builder()
            .chatId(userId)
            .text(message.getText())
            .build();

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}