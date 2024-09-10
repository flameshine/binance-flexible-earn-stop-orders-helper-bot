package com.flameshine.crypto.binance.helper.handler.button.impl.key;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.entity.Key;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.KeyboardMarkups;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("keyRemoveButtonHandler")
class RemoveButtonHandler implements ButtonHandler {

    @Override
    @Transactional
    public Response handle(CallbackQuery query) {

        var message = query.getMessage();
        var chatId = message.getChatId();
        var keys = Key.findAllByTelegramUserId(query.getFrom().getId());

        if (keys.isEmpty()) {

            var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(Messages.EMPTY_KEY_LIST)
                .build();

            return new Response(
                List.of(sendMessage)
            );
        }

        var text = EditMessageText.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .text(Messages.KEY_REMOVAL)
            .build();

        var markup = EditMessageReplyMarkup.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .replyMarkup(KeyboardMarkups.keyList(keys, true))
            .build();

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return new Response(
            List.of(answer, text, markup)
        );
    }
}