package com.flameshine.crypto.helper.bot.handler.message;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.bot.model.Response;

/**
 * Handles text messages sent by a user.
 */

public interface MessageHandler {
    Response handle(Message message);
}