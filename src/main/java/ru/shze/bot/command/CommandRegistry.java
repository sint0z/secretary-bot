package ru.shze.bot.command;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandRegistry {
    private final Map<String, BotCommand> commandMap = new HashMap<>();

    @Getter
    private BotCommand defaultCommand;

    @Autowired
    public void init(
            List<BotCommand> commands) {
        final Map<String, Class<?>> conflictNameMap = new HashMap<>();

        for (BotCommand cmd: commands) {
            String trigger = cmd.trigger();
            // Пропускаем пустую команду
            if(trigger.isEmpty()){
                continue;
            }
            registerCommand(trigger, cmd);

            for (String alias : cmd.aliases()) {
                registerCommand(alias, cmd);
            }
        }
    }

    @Autowired
    public void setDefaultCommand(
            @Qualifier("defaultCommand") BotCommand defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    public BotCommand getCommand(String commandName) {
        return commandMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(commandName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    private void registerCommand(String trigger, BotCommand command) {
        if(!commandMap.containsKey(trigger)){
            commandMap.put(trigger, command);
        }
    }


}