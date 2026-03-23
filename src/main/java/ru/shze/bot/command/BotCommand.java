package ru.shze.bot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

/**
 * Интерфейс для создания команд бота
 * *Данная версия несет временный характер*
 * @author Стоцкий Никита (Sint0z)
 * @version 1.0
 */
public interface BotCommand {

    /**
     * Возвращает основную комманду бота,
     * название комманд должно начинаться с символа "/" и alias
     * например: /start
     * @return основная команда бота
     * **/
    String trigger();

    default List<String> aliases(){
        return List.of();
    };

    /**
     * Выполняет действие команды и возвращает обобщенный ответ бота,
     * @param update структура данных от телеграмма полученная от бота,
     * @return обобщенный ответ бота
     * @see BotApiMethod
     * @see Update
     *
     */
    BotApiMethod<?> execute(Update update);
}
