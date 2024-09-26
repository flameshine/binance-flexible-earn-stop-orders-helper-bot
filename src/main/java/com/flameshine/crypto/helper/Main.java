package com.flameshine.crypto.helper;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.flameshine.crypto.helper.bot.BinanceFlexibleEarnStopLimitsHelperBot;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        Quarkus.run(Application.class, args);
    }

    private static class Application implements QuarkusApplication {

        private final BinanceFlexibleEarnStopLimitsHelperBot bot;

        @Inject
        public Application(BinanceFlexibleEarnStopLimitsHelperBot bot) {
            this.bot = bot;
        }

        @Override
        public int run(String... args) throws Exception {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            Quarkus.waitForExit();
            return 0;
        }
    }
}