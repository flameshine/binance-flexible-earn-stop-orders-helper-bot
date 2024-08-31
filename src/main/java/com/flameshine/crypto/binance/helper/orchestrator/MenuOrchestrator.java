package com.flameshine.crypto.binance.helper.orchestrator;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.AccountMenuButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.MainMenuButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;

public class MenuOrchestrator {

    private final ButtonHandler mainMenuButtonHandler;
    private final ButtonHandler accountMenuButtonHandler;

    public MenuOrchestrator() {
        this.mainMenuButtonHandler = new MainMenuButtonHandler();
        this.accountMenuButtonHandler = new AccountMenuButtonHandler();
    }

    public HandlerResponse orchestrate(CallbackQuery query) {
        var keyboard = Keyboard.fromButtonData(query.getData());
        return switch (keyboard) {
            case MAIN_MENU -> mainMenuButtonHandler.handle(query);
            case ACCOUNT_MENU -> accountMenuButtonHandler.handle(query);
            case ORDER_MENU -> throw new UnsupportedOperationException();
        };
    }
}