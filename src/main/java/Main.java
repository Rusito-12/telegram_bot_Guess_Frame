import bot.GuessFrame;
import command.CommandDispatcher;
import config.Config;
import config.ConfigReader;
import config.ConfigReaderEnvironment;
import game.GameManager;
import game.GameService;
import game.Movie;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import parser.MovieParser;
import telegram.TelegramService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReaderEnvironment();
        Config config = configReader.read();

        List<Movie> movies = new MovieParser().parseMovie("/movies.csv");
        System.out.println(movies);

        TelegramClient telegramClient = new OkHttpTelegramClient(config.botApiToken());
        GameManager gameManager = new GameManager();

        TelegramService telegramService = new TelegramService(telegramClient);
        GameService gameService = new GameService(gameManager, telegramService);


        CommandDispatcher dispatcher = new CommandDispatcher(gameManager, telegramService, movies);
        GuessFrame frame = new GuessFrame(dispatcher, gameService);

        try(TelegramBotsLongPollingApplication botsLongApplication = new TelegramBotsLongPollingApplication()){
            botsLongApplication.registerBot(config.botApiToken(),
                   frame);

            System.out.println("Бот запущен!");
            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
