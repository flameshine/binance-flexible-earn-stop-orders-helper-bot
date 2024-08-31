package com.flameshine.crypto.binance.helper.model;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import com.flameshine.crypto.binance.helper.enums.UserState;

public record HandlerResponse(
    List<? extends BotApiMethod<?>> methods,
    UserState userState
) {
    public HandlerResponse(List<? extends BotApiMethod<?>> methods) {
        this(methods, UserState.STATELESS);
    }
}