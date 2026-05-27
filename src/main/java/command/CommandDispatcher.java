package command;

import game.GameManager;
import game.Movie;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.TelegramService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandDispatcher {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandDispatcher(GameManager gameManager, TelegramService service, List<Movie> movies) {
       commands.put("/start", new StartCommand(service));
       commands.put("/newgame",  new NewGameCommand(gameManager, service, movies));
    }

    public void dispatch(String commandText, Update update) {
        String commandKey = commandText.split("\\s+")[0].toLowerCase();

        Command command = commands.get(commandKey);

        System.out.printf("Обработка команды %s%n", commandKey);

        if (command != null) {
            command.execute(update);
        } else {
            System.out.printf("Команда %s не найдена%n", commandKey);
        }
    }


}
