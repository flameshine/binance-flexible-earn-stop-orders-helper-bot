package com.flameshine.crypto.helper.bot.handler.button.impl.order;

import java.util.List;

import com.google.common.base.Preconditions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.api.entity.Order;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.KeyboardMarkups;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("orderCancelButtonHandler")
class CancelButtonHandler implements ButtonHandler {

    @Override
    @Transactional
    public HandlerResponse handle(CallbackQuery query) {

        var message = query.getMessage();
        var chatId = message.getChatId();
        var account = Account.findByTelegramUserIdOptional(query.getFrom().getId());

        Preconditions.checkState(account.isPresent(), "Account should be present at this stage");

        var orders = Order.findAllByAccount(account.get());

        if (orders.isEmpty()) {

            var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(Messages.EMPTY_ORDER_LIST)
                .build();

            return new HandlerResponse(
                List.of(sendMessage)
            );
        }

        var text = EditMessageText.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .text(Messages.ORDER_CANCELLATION)
            .build();

        var markup = EditMessageReplyMarkup.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .replyMarkup(KeyboardMarkups.orderList(orders, true))
            .build();

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return new HandlerResponse(
            List.of(answer, text, markup)
        );
    }
}