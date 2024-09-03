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
import com.flameshine.crypto.binance.helper.handler.command.impl.HelpHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.MainMenuHandler;
import com.flameshine.crypto.binance.helper.handler.command.impl.StartHandler;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.orchestrator.MenuOrchestrator;

// TODO: review language options

@ApplicationScoped
public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot {

    private static final Map<Long, UserState> USER_STATE = new ConcurrentHashMap<>();

    private final MenuOrchestrator menuOrchestrator;
    private final CommandHandler startHandler;
    private final CommandHandler mainMenuHandler;
    private final CommandHandler helpHandler;
    private final MessageHandler apiKeyHandler;
    private final String username;

    @Inject
    public BinanceFlexibleEarnStopLimitsHelperBot(MessageHandler apiKeyHandler, BotConfig config) {
        super(config.token());
        this.menuOrchestrator = new MenuOrchestrator();
        this.startHandler = new StartHandler();
        this.mainMenuHandler = new MainMenuHandler();
        this.helpHandler = new HelpHandler();
        this.apiKeyHandler = apiKeyHandler;
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
            case WAITING_FOR_API_KEY -> apiKeyHandler.handle(message);
        };

        USER_STATE.put(chatId, response.userState());

        return response;
    }

    private HandlerResponse handleCommand(Update update) {
        var command = Command.fromValue(update.getMessage().getText());
        return switch (command) {
            case START -> startHandler.handle(update);
            case MENU -> mainMenuHandler.handle(update);
            case HELP -> helpHandler.handle(update);
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