package com.flameshine.crypto.binance.helper;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.flameshine.crypto.binance.helper.bot.BinanceFlexibleEarnStopLimitsHelperBot;

// TODO: set up logging
// TODO: review language options
// TODO: add proper README

@QuarkusMain
public class Application implements QuarkusApplication {

    private final BinanceFlexibleEarnStopLimitsHelperBot bot;

    @Inject
    public Application(BinanceFlexibleEarnStopLimitsHelperBot bot) {
        this.bot = bot;
    }

    @Override
    public int run(String... args) throws Exception {
        var botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        return 0;
    }
}