package com.flameshine.crypto.binance.helper.handler.button.impl.order;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.enums.OrderMenuButton;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

// TODO: finalize

@ApplicationScoped
@Named("orderMenuButtonHandler")
public class OrderMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler newButtonHandler;
    private final ButtonHandler listButtonHandler;

    @Inject
    public OrderMenuButtonHandler(
        @Named("orderListButtonHandler") ButtonHandler listButtonHandler
    ) {
        this.newButtonHandler = new NewButtonHandler();
        this.listButtonHandler = listButtonHandler;
    }

    @Override
    public Response handle(CallbackQuery query) {

        var message = query.getMessage();

        var text = EditMessageText.builder()
            .chatId(message.getChatId())
            .messageId(message.getMessageId())
            .text("")
            .build();

        var markup = EditMessageReplyMarkup.builder()
            .chatId(message.getChatId())
            .messageId(message.getMessageId())
            .build();

        var buttonData = OrderMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case USER_ORDERS -> {
                return listButtonHandler.handle(query);
            }

            case NEW -> {
                return newButtonHandler.handle(query);
            }

            case CANCEL -> {}

            case BACK -> {
                text.setText(Messages.MAIN_MENU);
                markup.setReplyMarkup(Keyboard.MAIN.getMarkup());
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