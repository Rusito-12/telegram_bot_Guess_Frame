package game;

import java.util.*;

public class GameManager {

    private final Map<String, GameSession> gameSessions =  new HashMap<>();
    private final int MAX_QUESTIONS = 10;

    public GameSession startNewGame(List<Movie> movies, String playerId) {
        List<Movie> selectedMovieList = new ArrayList<>(movies);
        Collections.shuffle(selectedMovieList);

        if (selectedMovieList.size() > MAX_QUESTIONS) {
            selectedMovieList = new ArrayList<>(selectedMovieList.subList(0, MAX_QUESTIONS));
        }

        GameSession gameSession = new GameSession(selectedMovieList, playerId);
        gameSessions.put(playerId, gameSession);
        return gameSession;
    }

    public GameSession findGameSession(String playerId) {
       return gameSessions.get(playerId);
    }

    public void endGame(String playerId) {
        gameSessions.remove(playerId);
    }
}
