package com.flameshine.crypto.binance.helper.handler.button.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.entity.Account;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.KeyboardMarkups;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("accountListButtonHandler")
public class AccountListButtonHandler implements ButtonHandler {

    @Override
    @Transactional
    public HandlerResponse handle(CallbackQuery query) {

        var message = query.getMessage();
        var chatId = message.getChatId();
        var accounts = Account.findAllByTelegramUserId(query.getFrom().getId());

        if (accounts.isEmpty()) {

            var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(Messages.EMPTY_ACCOUNT_LIST)
                .build();

            return new HandlerResponse(
                List.of(sendMessage)
            );
        }

        var text = EditMessageText.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .text(Messages.ACCOUNT_LIST)
            .build();

        var markup = EditMessageReplyMarkup.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .replyMarkup(KeyboardMarkups.accountList(accounts))
            .build();

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return new HandlerResponse(
            List.of(answer, text, markup)
        );
    }
}