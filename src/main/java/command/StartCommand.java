package command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class StartCommand implements Command {

    private final String startText = """
            U+1F44B Привет! Я Guess Frame.
            Я показываю кадры, а ты угадываешь, из како-го они фильма.
            """; // Фильмы, сериалы и мультфильмы - все вперемешку.

    private TelegramClient telegramClient;

    public StartCommand(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }
    @Override
    public void execute(Update update) {
        SendMessage message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(startText)
                .build();

        try{
            telegramClient.execute(message);
        }catch (TelegramApiException e){
            System.out.println("Не удалось отправить сообщение в ответ на команду /start");
        }
    }
}
