package ru.shze.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.shze.QueueType;
import ru.shze.service.QueueManager;

@Component
@RequiredArgsConstructor
public class MessageSender implements Runnable {
    @Autowired
    final QueueManager manager;
    final SecretaryBot bot;

    @Override
    public void run() {

        while (true) {
            try {
                BotApiMethod<?> message = (BotApiMethod<?>)manager.take(QueueType.OUTPUT);
                bot.execute(message);
            } catch (InterruptedException | TelegramApiException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
