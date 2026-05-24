package command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class NewGameCommand implements Command {

    private final TelegramClient telegramClient;

    public NewGameCommand(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }


    @Override
    public void execute(Update update) {

    }
}
