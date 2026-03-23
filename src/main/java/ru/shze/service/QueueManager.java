package ru.shze.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import ru.shze.QueueType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class QueueManager {

    private final Map<QueueType, BlockingQueue<Object>> queues = new HashMap<>();

    public QueueManager() {
        for (QueueType type : QueueType.values()) {
            queues.put(type, new LinkedBlockingQueue<>());
        }
    }

    public void offer(QueueType type, Object obj) {
       BlockingQueue<Object> queue = queues.get(type);
       queue.offer(obj);
    }

    public Object take(QueueType type) throws InterruptedException {
        BlockingQueue<Object> queue = queues.get(type);
        return queue.take();
    }

    public boolean isEmpty(QueueType type) {
        return queues.get(type).isEmpty();
    }

}
