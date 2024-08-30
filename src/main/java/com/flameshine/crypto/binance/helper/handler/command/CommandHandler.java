package com.flameshine.crypto.binance.helper.handler.command;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

// TODO: get rid of the raw parameterized type usage warnings

public interface CommandHandler {
    List<BotApiMethod<?>> handle(Update update);
}