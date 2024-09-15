package com.flameshine.crypto.helper.bot.orchestrator.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.enums.Keyboard;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.handler.button.impl.main.MainMenuButtonHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.orchestrator.Orchestrator;
import com.flameshine.crypto.helper.bot.util.ItemRemovalPrefixes;

@ApplicationScoped
public class ButtonOrchestrator implements Orchestrator<CallbackQuery> {

    private final ButtonHandler mainMenuButtonHandler;
    private final ButtonHandler keyMenuButtonHandler;
    private final ButtonHandler orderMenuButtonHandler;
    private final ButtonHandler keyItemButtonHandler;
    private final ButtonHandler orderItemButtonHandler;

    @Inject
    public ButtonOrchestrator(
        @Named("keyMenuButtonHandler") ButtonHandler keyMenuButtonHandler,
        @Named("orderMenuButtonHandler") ButtonHandler orderMenuButtonHandler,
        @Named("keyItemButtonHandler") ButtonHandler keyItemButtonHandler,
        @Named("orderItemButtonHandler") ButtonHandler orderItemButtonHandler
    ) {
        this.mainMenuButtonHandler = new MainMenuButtonHandler();
        this.keyMenuButtonHandler = keyMenuButtonHandler;
        this.orderMenuButtonHandler = orderMenuButtonHandler;
        this.keyItemButtonHandler = keyItemButtonHandler;
        this.orderItemButtonHandler = orderItemButtonHandler;
    }

    @Override
    public Response orchestrate(CallbackQuery query) {

        var data = query.getData();

        if (isKeyItemToRemove(data)) {
            return keyItemButtonHandler.handle(query);
        }

        if (isOrderItemToRemove(data)) {
            return orderItemButtonHandler.handle(query);
        }

        var keyboard = Keyboard.fromButtonData(data);

        return switch (keyboard) {
            case MAIN -> mainMenuButtonHandler.handle(query);
            case KEY -> keyMenuButtonHandler.handle(query);
            case ORDER -> orderMenuButtonHandler.handle(query);
        };
    }

    private static boolean isKeyItemToRemove(String input) {
        return input.startsWith(ItemRemovalPrefixes.KEY);
    }

    private static boolean isOrderItemToRemove(String input) {
        return input.startsWith(ItemRemovalPrefixes.ORDER);
    }
}