package com.flameshine.crypto.helper.bot.handler.message.impl.key;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.bot.entity.Key;
import com.flameshine.crypto.helper.bot.enums.UserState;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("apiKeyMessageHandler")
public class KeySetupMessageHandler implements MessageHandler {

    @Override
    @Transactional
    public Response handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        if (message.getText().isBlank()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.KEY_SETUP_FAILURE)
                .build();

            return new Response(
                List.of(sendMessage),
                UserState.WAITING_FOR_KEY_DETAILS
            );
        }

        var key = Key.builder()
            .telegramUserId(message.getFrom().getId())
            .value(message.getText())
            .build();

        key.persist();

        var sendMessage = sendMessageBuilder
            .text(Messages.KEY_SETUP_SUCCESS)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }
}