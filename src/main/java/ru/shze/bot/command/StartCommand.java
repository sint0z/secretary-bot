package ru.shze.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.shze.bot.state.BotState;
import ru.shze.bot.state.StateHolder;

import java.util.List;

@Component
public class StartCommand extends AbstractCommand{

    public StartCommand(StateHolder stateHolder) {
        super(stateHolder);
    }

    @Override
    public String trigger() {
        return "/start";
    }

    @Override
    public List<String> aliases() {
        return List.of("/начать", "/старт");
    }

    @Override
    public BotState installedState() {
        return BotState.WAITING_ROLE;
    }

    @Override
    protected BotApiMethod<?> doExecute(Update update) {
        Message message = update.getMessage();

        return getMessage(message);
    }

    private SendMessage getMessage(Message message) {
        final ReplyKeyboardMarkup keyboardMarkup = createKeyboard();

        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("""
                    👋 *Привет !* Я бот-секретарь Никиты Алексеевича.
                    
                    Чтобы я правильно направлял ваши сообщения, выберите свою роль:
                    
                    📦   Заказчик → создание заказов и предложка
                    🏢   Работодатель → вакансии и отклик
                    💬   Личное сообщение → прямая связь
                    🛡️   Админ → управление (только для владельца)
                    
                    Просто нажмите нужную кнопку.
                    """)
                .replyMarkup(keyboardMarkup)
                .parseMode("Markdown")
                .build();
    }

    public ReplyKeyboardMarkup createKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("📦 Заказчик");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("🏢 Работодатель");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("💬 Личное сообщение");

        KeyboardRow row4 = new KeyboardRow();
        row4.add("🛡️ Админ");
        keyboardMarkup.setKeyboard(List.of(row1, row2, row3, row4));

        return keyboardMarkup;
    }

}
