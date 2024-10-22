package dk.martinersej.liarsbar.game.gamearea;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;

public class Bench {

    private final Block block;
    private ArmorStand armorStand;

    public Bench(Location location) {
        this.block = location.getBlock();
    }

    public void seat(Player player) {
        Location roundedLoc = block.getLocation().clone().add(0.5, 0.3, 0.5);
        roundedLoc.setYaw(this.getRelativeYaw());
        this.armorStand = block.getWorld().spawn(roundedLoc, ArmorStand.class);
        this.armorStand.setMarker(true);
        this.armorStand.setVisible(false);
        this.armorStand.setCustomName("bench");
        this.armorStand.setPassenger(player);
    }

    public void stand() {
        this.armorStand.remove();
        this.armorStand = null;
    }

    public boolean isSeated() {
        return this.armorStand != null;
    }

    private BlockFace getBlockFace() {
        if (block.getState().getData() instanceof Directional) {
            Directional directionalData = (Directional) block.getState().getData();
            return directionalData.getFacing();
        }
        return BlockFace.NORTH;
    }

    private float getRelativeYaw() {
        switch (this.getBlockFace()) {
            case NORTH:
                return 180;
            case EAST:
                return -90;
            case WEST:
                return 90;
            case SOUTH:
            default:
                return 0;
        }
    }

    public Player getPlayer() {
        if (armorStand == null) {
            return null;
        }
        return (Player) armorStand.getPassenger();
    }

    public Block getBlock() {
        return block;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void delete() {
        stand();
    }
}
