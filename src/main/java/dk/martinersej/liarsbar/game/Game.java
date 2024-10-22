package dk.martinersej.liarsbar.game;

import dk.martinersej.liarsbar.LiarsBar;
import dk.martinersej.liarsbar.game.games.deck.DeckPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Game implements Listener {

    // game
    private final GameType gameType;
    private GameArea gameArea;

    // player
    private final int minPlayers = 2; // Minimum number of players
    private final int maxPlayers = 4;
    private List<GamePlayer> players = new ArrayList<>();

    public Game(GameType gameType) {
        this.gameType = gameType;
        this.gameArea = LiarsBar.get().getAvailableGameArea();
        Bukkit.getPluginManager().registerEvents(this, LiarsBar.get());
    }

    public abstract void setupGame();

    public abstract void setupRound();

    public abstract void startRound();

    public abstract void endRound();

    public abstract void liarCheck();

    public void deleteGame() {
        HandlerList.unregisterAll(this);
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

    public GamePlayer getGamePlayer(Player player) {
        for (GamePlayer gamePlayer : players) {
            if (gamePlayer.getPlayer().equals(player)) {
                return gamePlayer;
            }
        }
        return null;
    }

    public GameArea getGameArea() {
        return gameArea;
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = getGamePlayer(player);
        if (gamePlayer != null) {
            event.setCancelled(true);
        }
    }
}
