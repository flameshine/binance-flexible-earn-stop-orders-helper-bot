package com.flameshine.crypto.binance.helper.handler.button;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.model.Response;

/**
 * Handles menu button taps.
 */

public interface ButtonHandler {
    Response handle(CallbackQuery query);
}