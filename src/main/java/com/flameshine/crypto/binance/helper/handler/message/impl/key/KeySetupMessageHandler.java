package com.flameshine.crypto.binance.helper.handler.message.impl.key;

import java.util.List;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.entity.Key;
import com.flameshine.crypto.binance.helper.enums.UserState;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("apiKeyMessageHandler")
public class KeySetupMessageHandler implements MessageHandler {

    private static final Pattern API_KEY_PATTERN = Pattern.compile("(\\S+)\\s*-\\s*(\\S+)");

    @Override
    @Transactional
    public Response handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        var matcher = API_KEY_PATTERN.matcher(message.getText());

        if (!matcher.matches()) {

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
            .label(matcher.group(1))
            .value(matcher.group(2))
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