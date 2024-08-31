package com.flameshine.crypto.binance.helper.handler.message;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.model.HandlerResponse;

/**
 * Handles messages sent by a user.
 */

public interface MessageHandler {
    HandlerResponse handle(Message message);
}