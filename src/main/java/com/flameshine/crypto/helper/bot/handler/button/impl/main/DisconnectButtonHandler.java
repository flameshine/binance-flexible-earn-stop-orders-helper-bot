package com.flameshine.crypto.helper.bot.handler.button.impl.main;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("disconnectButtonHandler")
public class DisconnectButtonHandler implements ButtonHandler {

    @Override
    @Transactional
    public HandlerResponse handle(CallbackQuery query) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(query.getMessage().getChatId());

        var key = Account.findByTelegramUserIdOptional(
            query.getFrom().getId()
        );

        if (key.isEmpty()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.MISSING_ACCOUNT)
                .build();

            return new HandlerResponse(
                List.of(sendMessage)
            );
        }

        key.ifPresent(PanacheEntityBase::delete);

        var sendMessage = sendMessageBuilder
            .text(Messages.DISCONNECT_SUCCESS)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}