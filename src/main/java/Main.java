import bot.GuessFrame;
import config.Config;
import config.ConfigReader;
import config.ConfigReaderEnvironment;
import game.GameManager;
import game.Movie;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import parser.MovieParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReaderEnvironment();
        Config config = configReader.read();

        List<Movie> movies = new MovieParser().parseMovie("/movies.csv");
        System.out.println(movies);

        TelegramClient telegramClient = new OkHttpTelegramClient(config.botApiToken());
        GameManager gameManager = new GameManager();

        try(TelegramBotsLongPollingApplication botsLongApplication = new TelegramBotsLongPollingApplication()){
            botsLongApplication.registerBot(config.botApiToken(),
                    new GuessFrame(telegramClient, gameManager, movies));

            System.out.println("Бот запущен!");
            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
