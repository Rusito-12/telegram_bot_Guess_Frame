package telegram;

import game.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.io.InputStream;

public class TelegramService {

    public static final int ANSWER_COLUMNS_COUNT = 2;
    private final TelegramClient telegramClient;

    private static final Logger LOG = LoggerFactory.getLogger(TelegramService.class);

    public TelegramService(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void sendNextMovie(String chatId, GameSession gameSession) {
        gameSession.getNextQuestion()
                .ifPresent(question -> sendPhoto(chatId, question.secretMovie().pathToImage(),
                        new KeyboardBuilder().build(question.answerOptions(), ANSWER_COLUMNS_COUNT)));

    }

    public void sendMessage(String chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        try{
            telegramClient.execute(message);
        }catch (TelegramApiException e){
            LOG.error("Ошибка отправки сообщения chatId={}", chatId, e);
        }

    }

    public void sendPhoto(String chatId, String imagePath, ReplyKeyboard keyboard) {
        try(InputStream inputStream = getClass().getResourceAsStream(imagePath)){

            if (inputStream == null) {
                LOG.error("Не найдено изображение: {}", imagePath);
                return;
            }

            InputFile inputFile = new InputFile(inputStream, imagePath);

            SendPhoto.SendPhotoBuilder photo = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(inputFile)
                    .caption("Угадай фильм!");

            if (keyboard != null) {
                photo.replyMarkup(keyboard);
            }

            telegramClient.execute(photo.build());
        }catch (TelegramApiException | IOException e){
            e.printStackTrace();
        }
    }
}
