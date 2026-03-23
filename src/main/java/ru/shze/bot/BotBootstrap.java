package ru.shze.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
@RequiredArgsConstructor
public class BotBootstrap {

    private final SecretaryBot secretaryBot;
    private final TelegramBotsApi api;

    private final MessageSender sender;
    private final MessageReceiver receiver;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {

        try {
            api.registerBot(secretaryBot);

            Thread senderThread = new Thread(sender);
            senderThread.setDaemon(true);
            senderThread.start();

            Thread resiverThread = new Thread(receiver);
            resiverThread.setDaemon(true);
            resiverThread.start();

            log.info("Initializing bot...");
        }catch(TelegramApiException e){
            log.error("Error while initializing bot", e);
        }
    }
}
