package com.flameshine.crypto.helper.bot.handler.button;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.model.Response;

/**
 * Handles menu button taps.
 */

public interface ButtonHandler {
    Response handle(CallbackQuery query);
}