package com.flameshine.crypto.helper.bot.orchestrator.impl;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.helper.bot.enums.Command;
import com.flameshine.crypto.helper.bot.handler.message.impl.command.HelpCommandHandler;
import com.flameshine.crypto.helper.bot.handler.message.impl.command.MainMenuCommandHandler;
import com.flameshine.crypto.helper.bot.handler.message.impl.command.StartCommandHandler;
import com.flameshine.crypto.helper.bot.handler.message.MessageHandler;
import com.flameshine.crypto.helper.bot.model.HandlerResponse;
import com.flameshine.crypto.helper.bot.orchestrator.Orchestrator;

public class CommandOrchestrator implements Orchestrator<Message> {

    private final MessageHandler startCommandHandler;
    private final MessageHandler mainMenuCommandHandler;
    private final MessageHandler helpCommandHandler;

    public CommandOrchestrator() {
        this.startCommandHandler = new StartCommandHandler();
        this.mainMenuCommandHandler = new MainMenuCommandHandler();
        this.helpCommandHandler = new HelpCommandHandler();
    }

    @Override
    public HandlerResponse orchestrate(Message message) {
        var command = Command.fromValue(message.getText());
        return switch (command) {
            case START -> startCommandHandler.handle(message);
            case MENU -> mainMenuCommandHandler.handle(message);
            case HELP -> helpCommandHandler.handle(message);
        };
    }
}