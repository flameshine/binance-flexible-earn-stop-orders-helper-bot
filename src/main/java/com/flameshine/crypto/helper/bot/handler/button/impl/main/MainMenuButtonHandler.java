package com.flameshine.crypto.helper.bot.handler.button.impl.main;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.enums.Keyboard;
import com.flameshine.crypto.helper.bot.enums.MainMenuButton;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("mainMenuButtonHandler")
public class MainMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler supportButtonHandler;
    private final ButtonHandler disconnectButtonHandler;

    @Inject
    public MainMenuButtonHandler(
        @Named("disconnectButtonHandler") ButtonHandler disconnectButtonHandler
    ) {
        this.supportButtonHandler = new SupportButtonHandler();
        this.disconnectButtonHandler = disconnectButtonHandler;
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

        var buttonData = MainMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case ORDERS -> {
                text.setText(Messages.ORDER_MENU);
                markup.setReplyMarkup(Keyboard.ORDER.getMarkup());
            }

            case SUPPORT -> {
                return supportButtonHandler.handle(query);
            }

            case DISCONNECT -> {
                return disconnectButtonHandler.handle(query);
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