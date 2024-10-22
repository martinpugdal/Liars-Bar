package dk.martinersej.liarsbar.game;

import dk.martinersej.liarsbar.game.gamearea.Bench;
import org.bukkit.Location;

public class GameArea {

    private final Location pos1, pos2; // corners of the game area
    private final Location tableCenter; // center of the table

    // bench
    private final Bench[] benches = new Bench[4];

    public GameArea(Location tableCenter) {
        // setup region of the game area
        int regionSize = 15;
        int regionHeight = 5;
        this.pos1 = tableCenter.clone().add(-regionSize, 0, -regionSize);
        this.pos2 = tableCenter.clone().add(regionSize, regionHeight, regionSize);

        // setup benches around the table
        this.tableCenter = tableCenter;
        int distanceToTable = 2;
        for (int i = 0; i < benches.length; i++) {
            // benches are placed around the table in an X shape with the tableCenter as the center
            for (int x = -distanceToTable; x <= distanceToTable; x += distanceToTable * 2) {
                for (int z = -distanceToTable; z <= distanceToTable; z += distanceToTable * 2) {
                    benches[i] = new Bench(tableCenter.clone().add(x, 0, z));
                }
            }
        }
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
