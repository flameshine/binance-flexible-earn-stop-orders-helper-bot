package com.flameshine.crypto.binance.helper.bot;

import java.util.Arrays;
import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.flameshine.crypto.binance.helper.config.BotConfig;
import com.flameshine.crypto.binance.helper.enums.Command;
import com.flameshine.crypto.binance.helper.handler.command.CommandHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.MainMenuHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.StartHandler;
import com.flameshine.crypto.binance.helper.orchestrator.MenuOrchestrator;

// TODO: add Binance account set up before the menu

@ApplicationScoped
public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot {

    private final CommandHandler startHandler;
    private final CommandHandler mainMenuHandler;
    private final MenuOrchestrator menuOrchestrator;
    private final String username;

    @Inject
    public BinanceFlexibleEarnStopLimitsHelperBot(BotConfig config) {
        super(config.token());
        this.startHandler = new StartHandler();
        this.mainMenuHandler = new MainMenuHandler();
        this.menuOrchestrator = new MenuOrchestrator();
        this.username = config.username();
        registerCommands();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            var methods = menuOrchestrator.orchestrate(update.getCallbackQuery());
            methods.forEach(this::executeMethod);
            return;
        }

        var command = Command.fromValue(update.getMessage().getText());

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

    private void registerCommands() {

        var commands = Arrays.stream(Command.values())
            .map(command -> new BotCommand(command.toString(), command.getDescription()))
            .toList();

        var setMyCommands = SetMyCommands.builder()
            .commands(commands)
            .build();

        executeMethod(setMyCommands);
    }

    private void executeMethod(BotApiMethod<?> method) {
        try {
            execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}