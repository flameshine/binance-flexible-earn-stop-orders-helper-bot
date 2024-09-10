package com.flameshine.crypto.binance.helper.handler.button.impl.key;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.binance.helper.entity.Key;
import com.flameshine.crypto.binance.helper.handler.button.ButtonHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.util.KeyboardMarkups;
import com.flameshine.crypto.binance.helper.util.Messages;

@ApplicationScoped
@Named("keyItemButtonHandler")
public class KeyItemButtonHandler implements ButtonHandler {

    @Override
    @Transactional
    public Response handle(CallbackQuery query) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(query.getMessage().getChatId());

        var key = Key.findByLabel(
            extractKeyLabel(query.getData())
        );

        if (key == null) {

            var sendMessage = sendMessageBuilder
                .text(Messages.UNRECOGNIZED_KEY)
                .build();

            return new Response(
                List.of(sendMessage)
            );
        }

        Key.deleteById(key.id);

        var sendMessage = sendMessageBuilder
            .text(Messages.KEY_REMOVAL_SUCCESS)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }

    private static String extractKeyLabel(String input) {
        return input.replaceFirst(KeyboardMarkups.KEY_REMOVE_PREFIX, "");
    }
}