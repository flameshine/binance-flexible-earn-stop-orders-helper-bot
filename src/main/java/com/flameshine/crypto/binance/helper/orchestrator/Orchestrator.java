package com.flameshine.crypto.binance.helper.orchestrator;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import com.flameshine.crypto.binance.helper.model.Response;

public interface Orchestrator<T extends BotApiObject> {
    Response orchestrate(T input);
}