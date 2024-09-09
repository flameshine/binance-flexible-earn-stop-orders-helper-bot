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
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.flameshine.crypto.binance.helper.config.BotConfig;
import com.flameshine.crypto.binance.helper.enums.Command;
import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.handler.message.impl.UnrecognizedMessageHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.orchestrator.Orchestrator;
import com.flameshine.crypto.binance.helper.orchestrator.impl.CommandOrchestrator;

// TODO: review language options

@ApplicationScoped
public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot {

    private static final Map<Long, UserState> USER_STATE = new ConcurrentHashMap<>();

    private final Orchestrator<Message> commandOrchestrator;
    private final Orchestrator<CallbackQuery> menuButtonOrchestrator;
    private final MessageHandler apiKeyMessageHandler;
    private final MessageHandler accountDisconnectMessageHandler;
    private final MessageHandler orderDetailsMessageHandler;
    private final MessageHandler unrecognizedMessageHandler;
    private final String username;

    @Inject
    public BinanceFlexibleEarnStopLimitsHelperBot(
        Orchestrator<CallbackQuery> menuButtonOrchestrator,
        @Named("apiKeyMessageHandler") MessageHandler apiKeyMessageHandler,
        @Named("accountDisconnectMessageHandler") MessageHandler accountDisconnectMessageHandler,
        @Named("orderDetailsMessageHandler") MessageHandler orderDetailsMessageHandler,
        BotConfig config
    ) {
        super(config.token());
        this.commandOrchestrator = new CommandOrchestrator();
        this.menuButtonOrchestrator = menuButtonOrchestrator;
        this.apiKeyMessageHandler = apiKeyMessageHandler;
        this.accountDisconnectMessageHandler = accountDisconnectMessageHandler;
        this.orderDetailsMessageHandler = orderDetailsMessageHandler;
        this.unrecognizedMessageHandler = new UnrecognizedMessageHandler();
        this.username = config.username();
        registerCommands();
    }

    @Override
    public void onUpdateReceived(Update update) {

        Response response;

        if (isButtonTap(update)) {
            response = handleButtonTap(update.getCallbackQuery());
        } else {
            response = handleMessage(update.getMessage());
        }

        response.methods().forEach(this::executeMethod);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    private Response handleButtonTap(CallbackQuery query) {
        var response = menuButtonOrchestrator.orchestrate(query);
        USER_STATE.put(query.getFrom().getId(), response.state());
        return response;
    }

    private Response handleMessage(Message message) {

        var chatId = message.getChatId();

        if (message.isCommand()) {
            USER_STATE.put(chatId, UserState.STATELESS);
            return commandOrchestrator.orchestrate(message);
        }

        var state = USER_STATE.getOrDefault(chatId, UserState.STATELESS);

        var response = switch (state) {
            case STATELESS -> unrecognizedMessageHandler.handle(message);
            case WAITING_FOR_API_KEY -> apiKeyMessageHandler.handle(message);
            case WAITING_FOR_ACCOUNT_TO_DISCONNECT -> accountDisconnectMessageHandler.handle(message);
            case WAITING_FOR_ORDER_DETAILS -> orderDetailsMessageHandler.handle(message);
        };

        USER_STATE.put(chatId, response.state());

        return response;
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