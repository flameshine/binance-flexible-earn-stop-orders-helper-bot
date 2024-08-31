package com.flameshine.crypto.binance.helper.handler.button;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.model.HandlerResponse;

/**
 * Handles menu button taps.
 */

public interface ButtonHandler {
    HandlerResponse handle(CallbackQuery query);
}