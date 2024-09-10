package com.flameshine.crypto.binance.helper.orchestrator.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.main.MainMenuButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.orchestrator.Orchestrator;
import com.flameshine.crypto.binance.helper.util.KeyboardMarkups;

@ApplicationScoped
public class ButtonOrchestrator implements Orchestrator<CallbackQuery> {

    private final ButtonHandler mainMenuButtonHandler;
    private final ButtonHandler keyMenuButtonHandler;
    private final ButtonHandler orderMenuButtonHandler;
    private final ButtonHandler keyItemButtonHandler;

    @Inject
    public ButtonOrchestrator(
        @Named("keyMenuButtonHandler") ButtonHandler keyMenuButtonHandler,
        @Named("orderMenuButtonHandler") ButtonHandler orderMenuButtonHandler,
        @Named("keyItemButtonHandler") ButtonHandler keyItemButtonHandler
    ) {
        this.mainMenuButtonHandler = new MainMenuButtonHandler();
        this.keyMenuButtonHandler = keyMenuButtonHandler;
        this.orderMenuButtonHandler = orderMenuButtonHandler;
        this.keyItemButtonHandler = keyItemButtonHandler;
    }

    @Override
    public Response orchestrate(CallbackQuery query) {

        if (isKeyItemToRemove(query.getData())) {
            return keyItemButtonHandler.handle(query);
        }

        var keyboard = Keyboard.fromButtonData(query.getData());

        return switch (keyboard) {
            case MAIN -> mainMenuButtonHandler.handle(query);
            case KEY -> keyMenuButtonHandler.handle(query);
            case ORDER -> orderMenuButtonHandler.handle(query);
        };
    }

    private static boolean isKeyItemToRemove(String input) {
        return input.startsWith(KeyboardMarkups.KEY_REMOVAL_PREFIX);
    }
}