package com.flameshine.crypto.helper.bot.handler.button.impl.order;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.enums.Keyboard;
import com.flameshine.crypto.helper.bot.enums.OrderMenuButton;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("orderMenuButtonHandler")
public class OrderMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler newButtonHandler;
    private final ButtonHandler listButtonHandler;
    private final ButtonHandler cancelButtonHandler;

    @Inject
    public OrderMenuButtonHandler(
        @Named("orderListButtonHandler") ButtonHandler listButtonHandler,
        @Named("orderCancelButtonHandler") ButtonHandler cancelButtonHandler
    ) {
        this.newButtonHandler = new NewButtonHandler();
        this.listButtonHandler = listButtonHandler;
        this.cancelButtonHandler = cancelButtonHandler;
    }

    @Override
    public HandlerResponse handle(CallbackQuery query) {

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

            case CANCEL -> {
                return cancelButtonHandler.handle(query);
            }

            case BACK -> {
                text.setText(Messages.MAIN_MENU);
                markup.setReplyMarkup(Keyboard.MAIN.getMarkup());
            }
        }

        var answer = AnswerCallbackQuery.builder()
            .callbackQueryId(query.getId())
            .build();

        return new HandlerResponse(
            List.of(answer, text, markup)
        );
    }
}