package com.flameshine.crypto.binance.helper;

import java.io.IOException;
import java.util.Properties;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.flameshine.crypto.binance.helper.bot.BinanceFlexibleEarnStopLimitsHelperBot;
import com.flameshine.crypto.binance.helper.config.BotConfig;
import com.flameshine.crypto.binance.helper.handler.impl.MainMenuButtonHandler;
import com.flameshine.crypto.binance.helper.handler.impl.MainMenuHandler;
import com.flameshine.crypto.binance.helper.handler.impl.StartHandler;

// TODO: set up logging
// TODO: configure commands programmatically
// TODO: review language options

public class Main {

    private static final BotConfig CONFIG;

    static {

        var properties = new Properties();

        try (var stream = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        CONFIG = new BotConfig(
            properties.getProperty("token"),
            properties.getProperty("username")
        );
    }

    public static void main(String... args) throws TelegramApiException {

        var botsApi = new TelegramBotsApi(DefaultBotSession.class);

        var bot = new BinanceFlexibleEarnStopLimitsHelperBot(
            new StartHandler(),
            new MainMenuHandler(),
            new MainMenuButtonHandler(),
            CONFIG
        );

        botsApi.registerBot(bot);
    }
}