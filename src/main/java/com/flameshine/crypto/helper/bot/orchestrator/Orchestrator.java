package com.flameshine.crypto.helper.bot.orchestrator;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import com.flameshine.crypto.helper.bot.model.HandlerResponse;

public interface Orchestrator<T extends BotApiObject> {
    HandlerResponse orchestrate(T input);
}