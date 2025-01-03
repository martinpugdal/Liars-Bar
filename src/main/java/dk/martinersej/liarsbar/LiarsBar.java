package dk.martinersej.liarsbar;

import dk.martinersej.liarsbar.commands.GamemodeCommand;
import dk.martinersej.liarsbar.commands.TestCommand;
import dk.martinersej.liarsbar.game.Game;
import dk.martinersej.liarsbar.game.GameArea;
import dk.martinersej.liarsbar.game.GameWorld;
import dk.martinersej.liarsbar.game.gamearea.Map;
import dk.martinersej.liarsbar.listeners.PlayerJoinListener;
import dk.martinersej.liarsbar.lobby.LobbyHandler;
import dk.martinersej.liarsbar.utils.gui.OnGuiClickListener;
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

        // Create game world
        GameWorld.getInstance().createWorld();

        // setup map
        getServer().getPluginManager().registerEvents(new Map(), this);

        new LobbyHandler();

        // Register commands
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("test").setExecutor(new TestCommand());

        // Register listeners
        getServer().getPluginManager().registerEvents(new OnGuiClickListener(), this); // for gui lib to work
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
