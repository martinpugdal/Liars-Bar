package dk.martinersej.liarsbar.game;

import dk.martinersej.liarsbar.game.games.deck.DeckPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    // game
    private final GameType gameType;
    private GameArea gameArea;

    // player
    private final int minPlayers = 2; // Minimum number of players
    private final int maxPlayers = 4;
    private List<GamePlayer> players = new ArrayList<>();

    public Game(GameType gameType) {
        this.gameType = gameType;
    }

    public abstract void setupGame();

    public abstract void startGame();

    public abstract void endGame();

    public void deleteGame() {
        // remove game area and this game from the game list
    }

    public void addPlayer(Player player) {
        switch (gameType) {
            case LIAR_DECK:
                addPlayer(new DeckPlayer(player));
                break;
//            case LIAR_DICE:
//                addPlayer(new DicePlayer(player));
//                break;

        }
    }

    public void addPlayer(GamePlayer player) {
        if (players.size() < maxPlayers) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.removeIf(gamePlayer -> gamePlayer.getPlayer().equals(player));
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public GameArea getGameArea() {
        return gameArea;
    }
}
