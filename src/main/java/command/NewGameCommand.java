package command;

import game.GameManager;
import game.GameSession;
import game.Movie;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.TelegramService;

import java.util.List;

public class NewGameCommand implements Command {


    private final GameManager gameManager;
    private final TelegramService telegramService;
    private final List<Movie> movies;

    public NewGameCommand(GameManager gameManager, TelegramService telegramService, List<Movie> movies) {
        this.gameManager = gameManager;
        this.telegramService = telegramService;
        this.movies = movies;
    }


    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        GameSession session = gameManager.startNewGame(movies, chatId);

        telegramService.sendNextMovie(chatId, session);
    }
}
