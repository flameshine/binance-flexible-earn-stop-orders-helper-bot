package com.flameshine.crypto.helper.bot.handler.message.impl.account;

import java.util.List;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.api.entity.Account;
import com.flameshine.crypto.helper.bot.enums.UserState;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.util.Messages;

@ApplicationScoped
@Named("userDetailsMessageHandler")
public class AccountDetailsMessageHandler implements MessageHandler {

    private static final Pattern USER_DETAILS_PATTERN = Pattern.compile("(^.+):(.+)$");

    @Override
    @Transactional
    public HandlerResponse handle(Message message) {

        var sendMessageBuilder = SendMessage.builder()
            .chatId(message.getChatId());

        var userId = message.getFrom().getId();
        var optionalAccount = Account.findByTelegramUserIdOptional(userId);

        if (optionalAccount.isPresent()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.ACCOUNT_ALREADY_EXISTS)
                .build();

            return new HandlerResponse(
                List.of(sendMessage)
            );
        }

        var matcher = USER_DETAILS_PATTERN.matcher(message.getText());

        if (!matcher.matches()) {

            var sendMessage = sendMessageBuilder
                .text(Messages.ACCOUNT_SETUP_FAILURE)
                .build();

            return new HandlerResponse(
                List.of(sendMessage),
                UserState.WAITING_FOR_ACCOUNT_DETAILS
            );
        }

        var account = Account.builder()
            .telegramUserId(userId)
            .apiKey(matcher.group(1))
            .secretKey(matcher.group(2))
            .build();

        account.persist();

        var sendMessage = sendMessageBuilder
            .text(Messages.ACCOUNT_SETUP_SUCCESS)
            .build();

        return new HandlerResponse(
            List.of(sendMessage)
        );
    }
}