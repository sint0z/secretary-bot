package ru.shze.bot.state;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStateHolder implements StateHolder{

    private final Map<Long, BotState> userStates = new ConcurrentHashMap<>();

    @Override
    public void setState(Long userId, BotState state) {
        userStates.put(userId, state);
    }

    @Override
    public BotState currentState(Long userId) {
        return userStates.getOrDefault(userId, BotState.EMPTY);
    }

    @Override
    public void removeState(Long userId) {
        BotState state = userStates.get(userId);

        if (state != null) {
            return;
        }

        userStates.put(userId, BotState.EMPTY);
    }
}
