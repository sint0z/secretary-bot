package ru.shze.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("defaultCommand")
public class NoCommand implements BotCommand {
    @Override
    public String trigger() {
        return "";
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        Message message = update.getMessage();
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                    😕 *Я не знаю такой команды.*
                    
                    Воспользуйтесь командой */help* — чтобы узнать список доступных команд.
                    
                    Если что-то пошло не так, попробуйте */start* — это поможет начать заново.
                    """)
                .parseMode("Markdown")
                .build();
    }
}
