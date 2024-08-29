package com.flameshine.crypto.binance.helper;

import java.io.IOException;
import java.util.Properties;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.flameshine.crypto.binance.helper.bot.BinanceFlexibleEarnStopLimitsHelperBot;

public class Main {

    private static final String TOKEN;
    private static final String USERNAME;

    static {

        var properties = new Properties();

        try (var stream = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        TOKEN = properties.getProperty("token");
        USERNAME = properties.getProperty("username");
    }

    public static void main(String... args) throws TelegramApiException {
        var botsApi = new TelegramBotsApi(DefaultBotSession.class);
        var bot = new BinanceFlexibleEarnStopLimitsHelperBot(TOKEN, USERNAME);
        botsApi.registerBot(bot);
    }
}