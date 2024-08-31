package com.flameshine.crypto.binance.helper.bot;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.command.CommandHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.MainMenuHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.StartHandler;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.handler.message.impl.ApiKeyNameHandler;
import com.flameshine.crypto.binance.helper.handler.message.impl.ApiKeyValueHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.orchestrator.MenuOrchestrator;

@ApplicationScoped
public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot {

    private static final Map<Long, UserState> USER_STATE = new ConcurrentHashMap<>();

    private final MenuOrchestrator menuOrchestrator;
    private final CommandHandler startHandler;
    private final CommandHandler mainMenuHandler;
    private final MessageHandler apiKeyValueHandler;
    private final MessageHandler apiKeyNameHandler;
    private final String username;

    @Inject
    public BinanceFlexibleEarnStopLimitsHelperBot(BotConfig config) {
        super(config.token());
        this.menuOrchestrator = new MenuOrchestrator();
        this.startHandler = new StartHandler();
        this.mainMenuHandler = new MainMenuHandler();
        this.apiKeyValueHandler = new ApiKeyValueHandler();
        this.apiKeyNameHandler = new ApiKeyNameHandler();
        this.username = config.username();
        registerCommands();
    }

    @Override
    public void onUpdateReceived(Update update) {

        // TODO: reduce branching

        if (!update.hasMessage()) {
            var response = handleUpdate(update);
            response.methods().forEach(this::executeMethod);
            return;
        }

        var chatId = update.getMessage().getChatId();
        var state = USER_STATE.getOrDefault(chatId, UserState.STATELESS);
        var response = handleUpdateBasedOnState(update, state);

        USER_STATE.put(chatId, response.userState());

        response.methods().forEach(this::executeMethod);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    private HandlerResponse handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            return menuOrchestrator.orchestrate(update.getCallbackQuery());
        }

        var message = update.getMessage();

        if (!message.isCommand()) {
            return mainMenuHandler.handle(update);
        }

        var command = Command.fromValue(message.getText());

        if (Command.START.equals(command)) {
            return startHandler.handle(update);
        }

        return mainMenuHandler.handle(update);
    }

    private HandlerResponse handleUpdateBasedOnState(Update update, UserState state) {
        var message = update.getMessage();
        return switch (state) {
            case STATELESS -> handleUpdate(update);
            case WAITING_FOR_API_KEY -> apiKeyValueHandler.handle(message);
            case WAITING_FOR_API_KEY_NAME -> apiKeyNameHandler.handle(message);
        };
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