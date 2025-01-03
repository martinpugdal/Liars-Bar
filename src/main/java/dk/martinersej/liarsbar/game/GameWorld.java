package dk.martinersej.liarsbar.game;

import dk.martinersej.liarsbar.LiarsBar;
import dk.martinersej.liarsbar.utils.FileUtils;
import dk.martinersej.liarsbar.utils.VoidGenerator;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;

public class GameWorld {

    private static GameWorld instance = null;

    private final int scale = 1000;
    private World world;
    private Location lastLocation;

    public GameWorld() {
        instance = this;
    }

    public static GameWorld getInstance() {
        if (instance == null) {
            instance = new GameWorld();
        }
        return instance;
    }

    public void createWorld() {
        if (world != null || Bukkit.getWorld(getClass().getSimpleName()) != null) {
            // delete the world if it already exists
            world = null;
            deleteGameWorld();
        }

        WorldCreator worldCreator = new WorldCreator(getClass().getSimpleName());
        worldCreator.generator(new VoidGenerator());
        worldCreator.type(WorldType.FLAT);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generateStructures(false);
        this.world = worldCreator.createWorld();
        this.world.setAutoSave(false);
        this.world.setGameRuleValue("doMobSpawning", "false");
        this.world.setGameRuleValue("randomTickSpeed", "0");
        this.world.setGameRuleValue("doDaylightCycle", "false");
        this.world.setGameRuleValue("doWeatherCycle", "false");
        this.world.setGameRuleValue("showDeathMessages", "false");
        this.world.setDifficulty(Difficulty.EASY);

        this.lastLocation = new Location(world, 0, 15, 0);
    }

    public void deleteGameWorld() {
        World gameWorld = Bukkit.getWorld(getClass().getSimpleName());

        if (gameWorld != null) {
            World world = Bukkit.getWorlds().get(0);
            for (Player player : gameWorld.getPlayers()) {
                player.teleport(world.getSpawnLocation());
            }
        }

        try {
            Bukkit.unloadWorld(getClass().getSimpleName(), false);
            this.world = null;
            this.lastLocation = null;
        } catch (ArrayIndexOutOfBoundsException ignored) {
            System.out.println("Failed unloading the world!");
        }

        FileUtils.deleteDir(new File(getClass().getSimpleName()));
    }

    public boolean isGameAreaExists(Location location) {
        return LiarsBar.get().getCurrentGames().stream().anyMatch(game -> game.getGameArea().isInside(location));
    }

    public Location getNextLocation() {
        // get the previous location
        int x = lastLocation.getBlockX();
        int z = lastLocation.getBlockZ();

        // determine the direction to move in
        int direction = (int) (Math.random() * 2);
        if (direction == 0) {
            x += scale;
        } else {
            z += scale;
        }

        // update the zero location
        lastLocation.setX(x);
        lastLocation.setZ(z);

        if (isGameAreaExists(lastLocation)) {
            return getNextLocation();
        } else {
            return lastLocation;
        }
    }

    public World getWorld() {
        return world;
    }
}
