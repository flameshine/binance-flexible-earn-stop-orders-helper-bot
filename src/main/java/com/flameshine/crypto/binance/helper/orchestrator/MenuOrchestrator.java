package com.flameshine.crypto.binance.helper.orchestrator;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.AccountMenuButtonHandler;
import com.flameshine.crypto.binance.helper.handler.button.impl.MainMenuButtonHandler;

public class MenuOrchestrator {

    private final ButtonHandler mainMenuButtonHandler;
    private final ButtonHandler accountMenuButtonHandler;

    public MenuOrchestrator() {
        this.mainMenuButtonHandler = new MainMenuButtonHandler();
        this.accountMenuButtonHandler = new AccountMenuButtonHandler();
    }

    public List<BotApiMethod<?>> orchestrate(CallbackQuery query) {
        var keyboard = Keyboard.fromButtonCallback(query.getData());
        return switch (keyboard) {
            case MAIN_MENU -> mainMenuButtonHandler.handle(query);
            case ACCOUNT_MENU -> accountMenuButtonHandler.handle(query);
            case ORDER_MENU -> throw new UnsupportedOperationException();
        };
    }
}