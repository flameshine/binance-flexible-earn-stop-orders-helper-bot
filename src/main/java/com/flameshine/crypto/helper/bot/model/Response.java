package com.flameshine.crypto.helper.bot.model;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import com.flameshine.crypto.helper.bot.enums.UserState;

public record Response(
    List<? extends BotApiMethod<?>> methods,
    UserState state
) {
    public Response(List<? extends BotApiMethod<?>> methods) {
        this(methods, UserState.STATELESS);
    }
}