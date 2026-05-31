package command;

import game.GameManager;
import game.Movie;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.TelegramService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(CommandDispatcher.class);

    private final Map<String, Command> commands = new HashMap<>();

    public CommandDispatcher(GameManager gameManager, TelegramService service, List<Movie> movies) {
       commands.put("/start", new StartCommand(service));
       commands.put("/newgame",  new NewGameCommand(gameManager, service, movies));
    }

    public void dispatch(String commandText, Update update) {
        String commandKey = commandText.split("\\s+")[0].toLowerCase();

        Command command = commands.get(commandKey);

        Long userId = update.getMessage().getFrom().getId();

        LOG.info("Получена команда: {} от пользоветеля {}", commandKey,  userId);

        if (command != null) {
            LOG.info("Команда выполнена: {} для пользователя {}", commandKey, userId);
            command.execute(update);
        } else {
            LOG.warn("Неизвестная команда: {} от пользователя {}", commandKey, userId);
        }
    }


}
