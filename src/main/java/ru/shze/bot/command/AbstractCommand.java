package ru.shze.bot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.shze.bot.state.BotState;
import ru.shze.bot.state.StateHolder;

/**
 * Абстрактная команда, убирает логику установки состояния бота
 * @author Стоцкий Никита (Sint0z)
 * @see BotCommand
 * @version 1.0
 * **/
public abstract class AbstractCommand implements BotCommand {

    protected final StateHolder stateHolder;

    public AbstractCommand(StateHolder stateHolder){
        this.stateHolder = stateHolder;
    }

    public abstract BotState installedState();

    protected abstract BotApiMethod<?> doExecute(Update update);

    @Override
    public final BotApiMethod<?> execute(Update update) {
        BotApiMethod<?> response = doExecute(update);
        BotState state = installedState();
        if (state != null) {
            stateHolder.setState(update.getMessage().getChatId(), state);
        }
        return response;
    }

}
