package game;

import java.util.*;

public class GameManager {

    private final Map<String, GameSession> gameSessions =  new HashMap<>();

    public GameSession startNewGame(List<Movie> movies, String playerId) {
        List<Movie> selectedMovieList = new ArrayList<>(movies);
        Collections.shuffle(selectedMovieList);
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
