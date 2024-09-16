package com.flameshine.crypto.helper.bot.orchestrator.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.enums.Keyboard;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.orchestrator.Orchestrator;
import com.flameshine.crypto.helper.bot.util.KeyboardMarkups;

@ApplicationScoped
public class ButtonOrchestrator implements Orchestrator<CallbackQuery> {

    private final ButtonHandler mainMenuButtonHandler;
    private final ButtonHandler orderMenuButtonHandler;
    private final ButtonHandler orderItemButtonHandler;

    @Inject
    public ButtonOrchestrator(
        @Named("mainMenuButtonHandler") ButtonHandler mainMenuButtonHandler,
        @Named("orderMenuButtonHandler") ButtonHandler orderMenuButtonHandler,
        @Named("orderItemButtonHandler") ButtonHandler orderItemButtonHandler
    ) {
        this.mainMenuButtonHandler = mainMenuButtonHandler;
        this.orderMenuButtonHandler = orderMenuButtonHandler;
        this.orderItemButtonHandler = orderItemButtonHandler;
    }

    @Override
    public Response orchestrate(CallbackQuery query) {

        var data = query.getData();

        if (isOrderItemToRemove(data)) {
            return orderItemButtonHandler.handle(query);
        }

        var keyboard = Keyboard.fromButtonData(data);

        return switch (keyboard) {
            case MAIN -> mainMenuButtonHandler.handle(query);
            case ORDER -> orderMenuButtonHandler.handle(query);
        };
    }

    private static boolean isOrderItemToRemove(String input) {
        return input.startsWith(KeyboardMarkups.ORDER_REMOVE_PREFIX);
    }
}