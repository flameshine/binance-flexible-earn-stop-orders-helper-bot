package com.flameshine.crypto.binance.helper.handler.command;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.flameshine.crypto.binance.helper.model.HandlerResponse;

/**
 * Handles commands sent by a user.
 */

public interface CommandHandler {
    HandlerResponse handle(Update update);
}