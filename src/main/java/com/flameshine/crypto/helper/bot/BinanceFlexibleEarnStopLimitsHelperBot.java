package com.flameshine.crypto.helper.bot;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.flameshine.crypto.helper.api.PriceTargetListener;
import com.flameshine.crypto.helper.api.handler.OrderExecutionHandler;
import com.flameshine.crypto.helper.bot.config.BotConfig;
import com.flameshine.crypto.helper.bot.enums.Command;
import com.flameshine.crypto.helper.bot.enums.UserState;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.handler.message.impl.UnrecognizedMessageHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.orchestrator.Orchestrator;
import com.flameshine.crypto.helper.bot.orchestrator.impl.CommandOrchestrator;
import com.flameshine.crypto.helper.bot.util.Messages;

// TODO: review language options
// TODO: execute actual orders
// TODO: investigate if it makes sense to redeem assets slightly earlier than the target price is reached

@ApplicationScoped
public class BinanceFlexibleEarnStopLimitsHelperBot extends TelegramLongPollingBot implements PriceTargetListener {

    private static final Map<Long, UserState> USER_STATE = new ConcurrentHashMap<>();

    private final Orchestrator<Message> commandOrchestrator;
    private final Orchestrator<CallbackQuery> buttonOrchestrator;
    private final MessageHandler userDetailsMessageHandler;
    private final MessageHandler orderDetailsMessageHandler;
    private final MessageHandler unrecognizedMessageHandler;
    private final OrderExecutionHandler orderExecutionHandler;
    private final String username;

    @Inject
    public BinanceFlexibleEarnStopLimitsHelperBot(
        Orchestrator<CallbackQuery> buttonOrchestrator,
        @Named("userDetailsMessageHandler") MessageHandler userDetailsMessageHandler,
        @Named("orderDetailsMessageHandler") MessageHandler orderDetailsMessageHandler,
        OrderExecutionHandler orderExecutionHandler,
        BotConfig config
    ) {
        super(config.token());
        this.commandOrchestrator = new CommandOrchestrator();
        this.buttonOrchestrator = buttonOrchestrator;
        this.userDetailsMessageHandler = userDetailsMessageHandler;
        this.orderDetailsMessageHandler = orderDetailsMessageHandler;
        this.unrecognizedMessageHandler = new UnrecognizedMessageHandler();
        this.orderExecutionHandler = orderExecutionHandler;
        this.username = config.username();
        registerCommands();
    }

    @Override
    public void onUpdateReceived(Update update) {

        HandlerResponse response;

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

    @Override
    public void onPriceReached(Long orderId) {

        var response = orderExecutionHandler.handle(orderId);
        var order = response.order();

        var text = switch (response.problem()) {
            case null -> Messages.orderExecutionSuccess(order);
            case BINANCE_API_PROBLEM -> Messages.orderExecutionFailure(order);
            case INVALID_PRODUCT_ID -> Messages.ORDER_EXECUTION_FAILURE_INSUFFICIENT_FUNDS;
        };

        var sendMessage = SendMessage.builder()
            .chatId(order.getAccount().getTelegramUserId())
            .text(text)
            .build();

        executeMethod(sendMessage);
    }

    private HandlerResponse handleButtonTap(CallbackQuery query) {
        var response = buttonOrchestrator.orchestrate(query);
        USER_STATE.put(query.getFrom().getId(), response.state());
        return response;
    }

    private HandlerResponse handleMessage(Message message) {

        var chatId = message.getChatId();

        if (message.isCommand()) {
            var response = commandOrchestrator.orchestrate(message);
            USER_STATE.put(chatId, response.state());
            return response;
        }

        var state = USER_STATE.getOrDefault(chatId, UserState.STATELESS);

        var response = switch (state) {
            case STATELESS -> unrecognizedMessageHandler.handle(message);
            case WAITING_FOR_ACCOUNT_DETAILS -> userDetailsMessageHandler.handle(message);
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