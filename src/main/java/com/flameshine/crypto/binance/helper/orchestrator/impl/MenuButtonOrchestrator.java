package com.flameshine.crypto.binance.helper.orchestrator.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.main.MainMenuButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.order.OrderMenuButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.orchestrator.Orchestrator;

@ApplicationScoped
public class MenuButtonOrchestrator implements Orchestrator<CallbackQuery> {

    private final ButtonHandler mainMenuButtonHandler;
    private final ButtonHandler accountMenuButtonHandler;
    private final ButtonHandler orderMenuButtonHandler;

    @Inject
    public MenuButtonOrchestrator(
        @Named("accountMenuButtonHandler") ButtonHandler accountMenuButtonHandler
    ) {
        this.mainMenuButtonHandler = new MainMenuButtonHandler();
        this.accountMenuButtonHandler = accountMenuButtonHandler;
        this.orderMenuButtonHandler = new OrderMenuButtonHandler();
    }

    @Override
    public Response orchestrate(CallbackQuery query) {
        var keyboard = Keyboard.fromButtonData(query.getData());
        return switch (keyboard) {
            case MAIN -> mainMenuButtonHandler.handle(query);
            case ACCOUNT -> accountMenuButtonHandler.handle(query);
            case ORDER -> orderMenuButtonHandler.handle(query);
        };
    }
}