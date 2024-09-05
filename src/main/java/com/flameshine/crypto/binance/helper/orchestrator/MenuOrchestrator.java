package com.flameshine.crypto.binance.helper.orchestrator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.MainMenuButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;

@ApplicationScoped
public class MenuOrchestrator {

    private final ButtonHandler mainMenuButtonHandler;
    private final ButtonHandler accountMenuButtonHandler;

    @Inject
    public MenuOrchestrator(@Named("accountMenuButtonHandler") ButtonHandler accountMenuButtonHandler) {
        this.mainMenuButtonHandler = new MainMenuButtonHandler();
        this.accountMenuButtonHandler = accountMenuButtonHandler;
    }

    public HandlerResponse orchestrate(CallbackQuery query) {
        var keyboard = KeyboardMenu.fromButtonData(query.getData());
        return switch (keyboard) {
            case MAIN -> mainMenuButtonHandler.handle(query);
            case ACCOUNT -> accountMenuButtonHandler.handle(query);
            case ORDER -> throw new UnsupportedOperationException();
        };
    }
}