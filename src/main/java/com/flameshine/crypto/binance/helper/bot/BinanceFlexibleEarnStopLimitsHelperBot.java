package com.flameshine.crypto.binance.helper.bot;

import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.flameshine.crypto.binance.helper.config.BotConfig;
import com.flameshine.crypto.binance.helper.enums.Command;
import com.flameshine.crypto.binance.helper.handler.ButtonHandler;
import com.flameshine.crypto.binance.helper.handler.UpdateHandler;

// TODO: add Binance account set up before the menu
// TODO: implement "Accounts" functionality
// TODO: implement "Orders" functionality

@ApplicationScoped
public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot {

    private final UpdateHandler startHandler;
    private final UpdateHandler mainMenuHandler;
    private final ButtonHandler mainMenuButtonHandler;
    private final String username;

    @Inject
    public BinanceFlexibleEarnStopLimitsHelperBot(
        @Named("StartHandler") UpdateHandler startHandler,
        @Named("MainMenuHandler") UpdateHandler mainMenuHandler,
        @Named("MainMenuButtonHandler") ButtonHandler mainMenuButtonHandler,
        BotConfig config
    ) {
        super(config.token());
        this.startHandler = startHandler;
        this.mainMenuHandler = mainMenuHandler;
        this.mainMenuButtonHandler = mainMenuButtonHandler;
        this.username = config.username();
    }

    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();

        // TODO: review this condition

        if (message == null) {
            var methods = mainMenuButtonHandler.handle(update.getCallbackQuery());
            methods.forEach(this::executeMethod);
            return;
        }

        var command = Command.fromValue(message.getText());

        if (Command.START.equals(command)) {

            var startMethods = startHandler.handle(update);
            var mainMenuMethods = mainMenuHandler.handle(update);

            Stream.concat(startMethods.stream(), mainMenuMethods.stream())
                .forEach(this::executeMethod);

        } else if (Command.MENU.equals(command)) {
            var methods = mainMenuHandler.handle(update);
            methods.forEach(this::executeMethod);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    private void executeMethod(BotApiMethod<?> method) {
        try {
            execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}