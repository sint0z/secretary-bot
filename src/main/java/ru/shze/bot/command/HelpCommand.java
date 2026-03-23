package ru.shze.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.shze.bot.state.BotState;
import ru.shze.bot.state.StateHolder;

@Component
public class HelpCommand extends AbstractCommand{
    public HelpCommand(StateHolder stateHolder) {
        super(stateHolder);
    }

    @Override
    public String trigger() {
        return "/help";
    }

    @Override
    public BotState installedState() {
        return BotState.HELP;
    }

    @Override
    protected BotApiMethod<?> doExecute(Update update) {
        String helpText = """
            📖 *Помощь по боту*
            
            Бот помогает разделять общение по ролям:
            • 📦 **Заказчик** — создавайте заказы, смотрите свои заказы и предложку.
            • 🏢 **Работодатель** — отправляйте вакансии, управляйте ими.
            • 💬 **Личное сообщение** — свяжитесь со мной, отправив сообщение.
            • 🛡️ **Админ** — доступно только создателю.
            
            ✨ *Основные команды:*
            /start — начать работу (или сменить роль)
            /help — эта справка
            /cancel — отменить текущее действие
            
            При возникновении вопросов пишите через кнопку "Личное сообщение".
            """;

        return SendMessage.builder()
                .text(helpText)
                .chatId(update.getMessage().getChatId())
                .parseMode("Markdown")
                .build();
    }
}
