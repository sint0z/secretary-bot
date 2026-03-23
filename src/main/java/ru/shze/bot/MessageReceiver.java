package ru.shze.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.shze.QueueType;
import ru.shze.bot.command.BotCommand;
import ru.shze.bot.command.CommandRegistry;
import ru.shze.bot.state.BotState;
import ru.shze.bot.state.StateHolder;
import ru.shze.service.QueueManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReceiver implements Runnable {

    final StateHolder stateHolder;
    final QueueManager manager;
    final CommandRegistry registry;

    @Override
    public void run() {
      while (true) {
          try {
              Update update = (Update) manager.take(QueueType.INPUT);

              if(update == null) continue;

              Message message = update.getMessage();

              BotApiMethod<?> outputMessage = null;
              BotCommand command = registry.getCommand(message.getText());
              if(command != null){
                  outputMessage = command.execute(update);
                  manager.offer(QueueType.OUTPUT, outputMessage);
                  continue;
              }

              BotState state = stateHolder.currentState(message.getFrom().getId());
              if(state != null){
                  outputMessage = switch (state) {
                      case EMPTY, HELP -> null;
                      case TEXT -> copyMessage(message);
                      case WAITING_ROLE -> handleMessage(message);
                  };
              }

              if(outputMessage == null) {
                  outputMessage = registry.getDefaultCommand().execute(update);
              }

              manager.offer(QueueType.OUTPUT, outputMessage);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
      }
    }


    private BotApiMethod<?> handleMessage(Message message) {
        if(message.getText().equals("💬 Личное сообщение")){
            SendMessage sendMessage = SendMessage.builder().chatId(message.getChatId()).text("Введите текст сообщения:")
                    .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build()).build();
            stateHolder.setState(message.getChatId(), BotState.TEXT);
            return sendMessage;
        }

        return null;
    }

    private BotApiMethod<?> copyMessage(Message message) {
        return CopyMessage.builder()
                .fromChatId(message.getChatId())
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .replyToMessageId(message.getMessageId())
                .build();
    }
}
