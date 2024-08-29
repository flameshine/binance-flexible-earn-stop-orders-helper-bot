package com.flameshine.crypto.binance.helper.handler;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

// TODO: get rid of raw parameterized type usage warnings

public interface UpdateHandler {
    List<BotApiMethod<?>> handle(Update update);
}