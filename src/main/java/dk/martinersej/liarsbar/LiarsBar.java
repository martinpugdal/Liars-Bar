package dk.martinersej.liarsbar;

import dk.martinersej.liarsbar.commands.GamemodeCommand;
import dk.martinersej.liarsbar.game.Game;
import dk.martinersej.liarsbar.game.GameArea;
import dk.martinersej.liarsbar.game.GameWorld;
import dk.martinersej.liarsbar.listeners.PlayerJoinListener;
import org.bukkit.event.HandlerList;
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

        // Register commands
        getCommand("gamemode").setExecutor(new GamemodeCommand());

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        GameWorld.getInstance().deleteGameWorld();

        HandlerList.unregisterAll();
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

    public GameArea getAvailableGameArea() {
        return new GameArea(GameWorld.getInstance().getNextLocation());
    }
}
