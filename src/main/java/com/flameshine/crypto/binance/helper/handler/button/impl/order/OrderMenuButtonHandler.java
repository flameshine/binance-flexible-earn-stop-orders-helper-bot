package com.flameshine.crypto.binance.helper.handler.button.impl.order;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.enums.OrderMenuButton;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

// TODO: finalize

public class OrderMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler newButtonHandler;

    public OrderMenuButtonHandler() {
        this.newButtonHandler = new NewButtonHandler();
    }

    @Override
    public Response handle(CallbackQuery query) {

        var message = query.getMessage();
        var chatId = message.getChatId();

        var text = EditMessageText.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .text("")
            .build();

        var markup = EditMessageReplyMarkup.builder()
            .chatId(chatId)
            .messageId(message.getMessageId())
            .build();

        var buttonData = OrderMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case NEW -> {
                return newButtonHandler.handle(query);
            }

            case USER_ORDERS -> {}

            case CANCEL -> {}

            case BACK -> {
                text.setText(Messages.MAIN_MENU);
                markup.setReplyMarkup(KeyboardMenu.MAIN.getMarkup());
            }
        }

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return new Response(
            List.of(answer, text, markup)
        );
    }
}