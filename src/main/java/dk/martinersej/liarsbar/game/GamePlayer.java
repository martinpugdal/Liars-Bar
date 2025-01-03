package dk.martinersej.liarsbar.game;

import org.bukkit.entity.Player;

public abstract class GamePlayer {

    private final Player player;
    private boolean isAlive = true;

    public GamePlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
