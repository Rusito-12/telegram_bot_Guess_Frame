package bot;

import command.CommandDispatcher;
import game.GameService;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GuessFrame implements LongPollingSingleThreadUpdateConsumer {

    private final CommandDispatcher dispatcher;
    private final GameService gameService;
    private static final Logger LOG = LoggerFactory.getLogger(GuessFrame.class);

    public GuessFrame(CommandDispatcher dispatcher, GameService gameService) {

        this.dispatcher = dispatcher;
        this.gameService = gameService;
    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            LOG.warn("Получено сообщение без текста!");
            return;
        }

        String text = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        Long userId = update.getMessage().getFrom().getId();

        LOG.info("Сообщение от userId={} в chatId={}: {}", userId, chatId, text);

        if (text.startsWith("/")) {
            LOG.info("Передаём команду в dispatcher: {} (userId={})", text, userId);
            dispatcher.dispatch(text, update);
            return;
        }else {
            LOG.info("Игрок {} отправил ответ: {}", userId, text);
            gameService.handleUserGuess(chatId, text);
        }
    }
}