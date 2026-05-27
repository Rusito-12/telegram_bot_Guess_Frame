package bot;

import command.CommandDispatcher;
import game.*;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;


public class GuessFrame implements LongPollingSingleThreadUpdateConsumer {

    private final CommandDispatcher dispatcher;
    private final GameService gameService;

    public GuessFrame(CommandDispatcher dispatcher, GameService gameService) {

        this.dispatcher = dispatcher;
        this.gameService = gameService;
    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            System.out.println("В сообщении пользователя нет текста!");
            return;
        }

        String text = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        if (text.startsWith("/")) {
            dispatcher.dispatch(text, update);
            return;
        }else {
            gameService.handleUserGuess(chatId, text);
        }
    }
}