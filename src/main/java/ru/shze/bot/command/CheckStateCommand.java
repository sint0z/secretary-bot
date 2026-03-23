package ru.shze.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.shze.bot.state.BotState;
import ru.shze.bot.state.StateHolder;

@Component
public class CheckStateCommand extends AbstractCommand{

    public CheckStateCommand(StateHolder stateHolder) {
        super(stateHolder);
    }

    @Override
    public BotState installedState() {
        return null;
    }

    @Override
    protected BotApiMethod<?> doExecute(Update update) {
        Long chatId = update.getMessage().getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Текущее состояние: " + stateHolder.currentState(chatId))
                .build();
    }

    @Override
    public String trigger() {
        return "/state";
    }
}
