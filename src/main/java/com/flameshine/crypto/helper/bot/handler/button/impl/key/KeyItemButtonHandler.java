package com.flameshine.crypto.helper.bot.handler.button.impl.key;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.flameshine.crypto.helper.bot.entity.Key;
import com.flameshine.crypto.helper.bot.handler.button.ButtonHandler;
import com.flameshine.crypto.helper.bot.model.Response;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("keyItemButtonHandler")
public class KeyItemButtonHandler implements ButtonHandler {

    @Override
    @Transactional
    public Response handle(CallbackQuery query) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(query.getMessage().getChatId());

        var key = Key.findByIdOptional(
            extractKeyId(query.getData())
        );

        if (key.isEmpty()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.UNRECOGNIZED_KEY)
                .build();

            return new Response(
                List.of(sendMessage)
            );
        }

        key.ifPresent(PanacheEntityBase::delete);

        var sendMessage = sendMessageBuilder
            .text(Messages.KEY_REMOVAL_SUCCESS)
            .build();

        return new Response(
            List.of(sendMessage)
        );
    }

    private static Long extractKeyId(String input) {
        try {
            return Long.parseLong(input.split("#")[1]);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(e);
        }
    }
}