package dk.martinersej.liarsbar.game;

import dk.martinersej.liarsbar.game.gamearea.Bench;
import dk.martinersej.liarsbar.game.gamearea.Map;
import jdk.nashorn.internal.ir.Block;
import org.bukkit.Location;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class GameArea {

    private final Location pos1, pos2; // corners of the game area
    private final Location tableCenter; // center of the table

    // bench
    private final Bench[] benches = new Bench[4];

    public GameArea(Location location) {
        this.pos1 = location; // location is pos1
        com.sk89q.worldedit.Vector pos2Vector = Map.getMapRegion().getMaximumPoint();
        this.pos2 = location.clone().add(pos2Vector.getBlockX(), pos2Vector.getBlockY(), pos2Vector.getBlockZ());

        // calculate the center of the table based on the pos1 and some counting ingame
        this.tableCenter = location.clone().add(0, 0, 0);

        // setup benches around the table
        int distanceToTable = 2;
        for (int i = 0; i < benches.length; i++) {
            // benches are placed around the table in an X shape with the tableCenter as the center
            for (int x = -distanceToTable; x <= distanceToTable; x += distanceToTable * 2) {
                for (int z = -distanceToTable; z <= distanceToTable; z += distanceToTable * 2) {
                    benches[i] = new Bench(tableCenter.clone().add(x, 0, z));
                }
            }
        }

        Map.loadMapAt(pos1);
    }

    public Location getTableCenter() {
        return tableCenter;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public boolean isInside(Location location) {
        return location.getBlockX() >= pos1.getBlockX() && location.getBlockX() <= pos2.getBlockX()
                && location.getBlockY() >= pos1.getBlockY() && location.getBlockY() <= pos2.getBlockY()
                && location.getBlockZ() >= pos1.getBlockZ() && location.getBlockZ() <= pos2.getBlockZ();
    }
}
