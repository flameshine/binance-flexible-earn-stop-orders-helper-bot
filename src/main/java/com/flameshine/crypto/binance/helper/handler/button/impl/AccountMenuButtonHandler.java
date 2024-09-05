package com.flameshine.crypto.binance.helper.handler.button.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.AccountMenuButton;
import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.HandlerResponse;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("accountMenuButtonHandler")
public class AccountMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler accountListButtonHandler;

    @Inject
    public AccountMenuButtonHandler(@Named("accountListButtonHandler") ButtonHandler accountListButtonHandler) {
        this.accountListButtonHandler = accountListButtonHandler;
    }

    @Override
    public HandlerResponse handle(CallbackQuery query) {

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

        var sendMessageBuilder = SendMessage.builder()
            .chatId(chatId);

        var buttonData = AccountMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case CONNECT -> {

                var sendMessage = sendMessageBuilder
                    .text(Messages.accountSetup())
                    .parseMode(ParseMode.MARKDOWNV2)
                    .build();

                return new HandlerResponse(
                    List.of(sendMessage),
                    UserState.WAITING_FOR_API_KEY
                );
            }

            case MY_ACCOUNTS -> {
                return accountListButtonHandler.handle(query);
            }

            case DISCONNECT -> {

                var sendMessage = sendMessageBuilder
                    .text(Messages.DISCONNECT)
                    .build();

                return new HandlerResponse(
                    List.of(sendMessage)
                );
            }

            case BACK -> {
                text.setText(Messages.MAIN_MENU);
                markup.setReplyMarkup(KeyboardMenu.MAIN.getMarkup());
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