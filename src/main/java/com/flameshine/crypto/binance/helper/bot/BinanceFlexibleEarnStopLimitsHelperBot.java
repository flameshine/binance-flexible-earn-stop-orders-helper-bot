package com.flameshine.crypto.binance.helper.bot;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
import com.flameshine.crypto.binance.helper.handler.command.impl.HelpCommandHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.MainMenuCommandHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.StartCommandHandler;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.orchestrator.MenuOrchestrator;

// TODO: review language options

@ApplicationScoped
public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot {

    private static final Map<Long, UserState> USER_STATE = new ConcurrentHashMap<>();

    private final MenuOrchestrator menuOrchestrator;
    private final CommandHandler startCommandHandler;
    private final CommandHandler mainMenuCommandHandler;
    private final CommandHandler helpCommandHandler;
    private final MessageHandler apiKeyMessageHandler;
    private final MessageHandler accountDisconnectionMessageHandler;
    private final String username;

    @Inject
    public BinanceFlexibleEarnStopLimitsHelperBot(
        MenuOrchestrator menuOrchestrator,
        @Named("apiKeyMessageHandler") MessageHandler apiKeyMessageHandler,
        @Named("accountDisconnectionMessageHandler") MessageHandler accountDisconnectionMessageHandler,
        BotConfig config
    ) {
        super(config.token());
        this.menuOrchestrator = menuOrchestrator;
        this.startCommandHandler = new StartCommandHandler();
        this.mainMenuCommandHandler = new MainMenuCommandHandler();
        this.helpCommandHandler = new HelpCommandHandler();
        this.apiKeyMessageHandler = apiKeyMessageHandler;
        this.accountDisconnectionMessageHandler = accountDisconnectionMessageHandler;
        this.username = config.username();
        registerCommands();
    }

    @Override
    public void onUpdateReceived(Update update) {

        HandlerResponse response;

        if (isButtonTap(update)) {
            response = handleButtonTap(update);
        } else {
            response = handleText(update);
        }

        response.methods().forEach(this::executeMethod);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    private HandlerResponse handleButtonTap(Update update) {
        var query = update.getCallbackQuery();
        var response = menuOrchestrator.orchestrate(query);
        USER_STATE.put(query.getFrom().getId(), response.userState());
        return response;
    }

    private HandlerResponse handleText(Update update) {

        var message = update.getMessage();
        var chatId = message.getChatId();
        var state = USER_STATE.getOrDefault(chatId, UserState.STATELESS);

        var response = switch (state) {
            case STATELESS -> handleCommand(update);
            case WAITING_FOR_API_KEY -> apiKeyMessageHandler.handle(message);
            case WAITING_FOR_ACCOUNT_TO_DISCONNECT -> accountDisconnectionMessageHandler.handle(message);
        };

        USER_STATE.put(chatId, response.userState());

        return response;
    }

    private HandlerResponse handleCommand(Update update) {
        var command = Command.fromValue(update.getMessage().getText());
        return switch (command) {
            case START -> startCommandHandler.handle(update);
            case MENU -> mainMenuCommandHandler.handle(update);
            case HELP -> helpCommandHandler.handle(update);
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

    private static boolean isButtonTap(Update update) {
        return update.hasCallbackQuery() && !update.hasMessage();
    }
}