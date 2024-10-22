package dk.martinersej.liarsbar;

import dk.martinersej.liarsbar.game.Game;
import dk.martinersej.liarsbar.game.GameWorld;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class LiarsBar extends JavaPlugin {

    private static LiarsBar instance;
    List<Game> currentGames = new ArrayList<>();

    public static LiarsBar get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        GameWorld.getInstance().deleteGameWorld();
    }

    public void addGame(Game game) {
        currentGames.add(game);
    }

    public void removeGame(Game game) {
        currentGames.remove(game);
    }

    public List<Game> getCurrentGames() {
        return currentGames;
    }
}
