package dk.martinersej.liarsbar.game.gamearea;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.Region;
import dk.martinersej.liarsbar.LiarsBar;
import dk.martinersej.liarsbar.game.GameWorld;
import dk.martinersej.liarsbar.utils.WorldEditUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import java.io.File;

public class Map implements Listener {

    private static Clipboard MAP;

    public static void init() {
        File mapFile = new File("plugins/LiarsBar/MAP.schematic");
        if (!mapFile.exists()) {
            // create the folder if it doesn't exist, so the user knows where to put the schematic file
            mapFile.getParentFile().mkdirs();
            throw new IllegalStateException("Map schematic file not found, please add a schematic file to plugins/LiarsBar/MAP.schematic");
        }
        MAP = WorldEditUtils.loadSchematic(mapFile, GameWorld.getInstance().getWorld());
    }

    @EventHandler
    public void onMapLoad(WorldLoadEvent event) {
        if (event.getWorld().getName().equals(GameWorld.class.getSimpleName())) {
            // delay the loading of the map schematic until the world is loaded
            Bukkit.getScheduler().runTaskLater(LiarsBar.get(), Map::init, 1L);
            HandlerList.unregisterAll(this);
        }
    }

    private static boolean isMapLoaded() {
        return MAP != null;
    }

    public static void loadMapAt(Location location) {
        if (!isMapLoaded()) {
            throw new IllegalStateException("Map schematic not loaded");
        }
        WorldEditUtils.pasteSchematic(MAP, location);
    }

    public static int getMapWidth() {
        if (!isMapLoaded()) {
            throw new IllegalStateException("Map schematic not loaded");
        }
        return MAP.getMinimumPoint().getBlockX() - MAP.getMaximumPoint().getBlockX();
    }

    public static int getMapHeight() {
        if (!isMapLoaded()) {
            throw new IllegalStateException("Map schematic not loaded");
        }
        return MAP.getMinimumPoint().getBlockY() - MAP.getMaximumPoint().getBlockY();
    }

    public static Region getMapRegion() {
        if (!isMapLoaded()) {
            throw new IllegalStateException("Map schematic not loaded");
        }
        return MAP.getRegion();
    }
}
