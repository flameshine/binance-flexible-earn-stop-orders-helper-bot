package com.flameshine.crypto.binance.helper.handler.button.impl.account;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.enums.AccountMenuButton;
import com.flameshine.crypto.binance.helper.enums.KeyboardMenu;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("accountMenuButtonHandler")
public class AccountMenuButtonHandler implements ButtonHandler {

    private final ButtonHandler listButtonHandler;
    private final ButtonHandler connectButtonHandler;
    private final ButtonHandler disconnectButtonHandler;

    @Inject
    public AccountMenuButtonHandler(
        @Named("accountListButtonHandler") ButtonHandler listButtonHandler
    ) {
        this.listButtonHandler = listButtonHandler;
        this.connectButtonHandler = new ConnectButtonHandler();
        this.disconnectButtonHandler = new DisconnectButtonHandler();
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

        var buttonData = AccountMenuButton.fromValue(query.getData());

        switch (buttonData) {

            case USER_ACCOUNTS -> {
                return listButtonHandler.handle(query);
            }

            case CONNECT -> {
                return connectButtonHandler.handle(query);
            }

            case DISCONNECT -> {
                return disconnectButtonHandler.handle(query);
            }

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