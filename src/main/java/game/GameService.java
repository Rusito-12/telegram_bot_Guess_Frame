package game;

import telegram.TelegramService;

import java.util.Optional;

public class GameService {

    private final GameManager gameManager;
    private TelegramService telegramService;

    public GameService(GameManager gameManager,  TelegramService telegramService) {
        this.gameManager = gameManager;
        this.telegramService = telegramService;
    }



    public void handleUserGuess(String chatId, String userMassageText) {
        GameSession gameSession = gameManager.findGameSession(chatId);

        if (gameSession == null) {
            telegramService.sendMessage(chatId, "Игра не начата! Для старта выполните команду /start");
            return;
        }

        Optional<Question> optionalQuestion = gameSession.getLastQuestion();
        if (optionalQuestion.isEmpty()){
            telegramService.sendMessage(chatId, "Вопросы закончились! Ваш счет: %d".formatted(gameSession.getScore()));
            gameManager.endGame(chatId);
            return;
        }

        Question lastQuestion = optionalQuestion.get();

        if (lastQuestion.isRightAnswer(userMassageText)) {
            gameSession.incrementScore();
            telegramService.sendMessage(chatId, "Правильно! Ваш текущий счет: %d".formatted(gameSession.getScore()));
        }else {
            telegramService.sendMessage(chatId, "К сожалению ответ неверный! Правильны ответ: %s"
                    .formatted(lastQuestion.secretMovie().title()));
        }

        if (gameSession.isGameFinished()){
            telegramService.sendMessage(chatId, "Игра окончена! Ваш итоговый счет: %d".formatted(gameSession.getScore()));
            return;
        }

        telegramService.sendNextMovie(chatId, gameSession);
    }

}
