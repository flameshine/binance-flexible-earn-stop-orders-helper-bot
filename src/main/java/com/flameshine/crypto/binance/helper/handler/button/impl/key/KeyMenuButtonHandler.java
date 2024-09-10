package com.flameshine.crypto.binance.helper.handler.button.impl.key;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.KeyMenuButton;
import com.flameshine.crypto.binance.helper.enums.Keyboard;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("keyMenuButtonHandler")
public class KeyMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler listButtonHandler;
    private final ButtonHandler addButtonHandler;
    private final ButtonHandler removeButtonHandler;

    @Inject
    public KeyMenuButtonHandler(
        @Named("keyListButtonHandler") ButtonHandler listButtonHandler,
        @Named("keyRemoveButtonHandler") ButtonHandler removeButtonHandler
    ) {
        this.listButtonHandler = listButtonHandler;
        this.addButtonHandler = new AddButtonHandler();
        this.removeButtonHandler = removeButtonHandler;
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

        var buttonData = KeyMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case USER_KEYS -> {
                return listButtonHandler.handle(query);
            }

            case ADD -> {
                return addButtonHandler.handle(query);
            }

            case REMOVE -> {
                return removeButtonHandler.handle(query);
            }

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