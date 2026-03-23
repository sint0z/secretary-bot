package ru.shze.bot.state;

/**
 * Интерфейс для хранения состояние пользователя использующего бота
 * @author Стоцкий Никита (Sint0z)
 **/
public interface StateHolder {

    void setState(Long userId, BotState state);

    BotState currentState(Long userId);

    void removeState(Long userId);
}
