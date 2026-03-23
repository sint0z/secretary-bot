package ru.shze.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.shze.QueueType;
import ru.shze.service.QueueManager;

@Slf4j
@Component
public class SecretaryBot extends TelegramLongPollingBot {

    final QueueManager queueManager;

    //TODO Перенести важные данные о боте в конфиг
    public SecretaryBot(QueueManager queueManager) {
        super("7881400581:AAGlvX8bRbfpO-y8vUN87sNzueWuK-umFmc");
        this.queueManager = queueManager;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();

        queueManager.offer(QueueType.INPUT, update);
        log.info("User @{} id:{} send message in chatId: {} \nMessage: [{}]", user.getUserName(), user.getId(), message.getChatId(), message.getText());
    }

    @Override
    public String getBotUsername() {
        return "SecretaryBot";
    }
}
