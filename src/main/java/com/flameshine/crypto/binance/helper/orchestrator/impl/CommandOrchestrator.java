package com.flameshine.crypto.binance.helper.orchestrator.impl;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.flameshine.crypto.binance.helper.enums.Command;
import com.flameshine.crypto.binance.helper.handler.message.impl.command.HelpCommandHandler;
import com.flameshine.crypto.binance.helper.handler.message.impl.command.MainMenuCommandHandler;
import com.flameshine.crypto.binance.helper.handler.message.impl.command.StartCommandHandler;
import com.flameshine.crypto.binance.helper.handler.message.MessageHandler;
import com.flameshine.crypto.binance.helper.model.Response;
import com.flameshine.crypto.binance.helper.orchestrator.Orchestrator;

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
    public Response orchestrate(Message message) {
        var command = Command.fromValue(message.getText());
        return switch (command) {
            case START -> startCommandHandler.handle(message);
            case MENU -> mainMenuCommandHandler.handle(message);
            case HELP -> helpCommandHandler.handle(message);
        };
    }
}