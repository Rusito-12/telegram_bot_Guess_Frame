package command;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.TelegramService;

public class StartCommand implements Command {

    private final TelegramService telegramService;

    public StartCommand(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        String startText = """
                👋 Привет! Я Guess Frame.
                Я показываю кадры, а ты угадываешь, из како-го они фильма.
                Для этого используйте команду /newgame
                """;

        String chatId = update.getMessage().getChatId().toString();

        telegramService.sendMessage(chatId, startText);
    }
}
