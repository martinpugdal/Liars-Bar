package dk.martinersej.liarsbar.game.games.deck;

import dk.martinersej.liarsbar.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RussianRoulettePistol {

    private final boolean[] chambers;
    private int currentPosition;

    public RussianRoulettePistol() {
        chambers = new boolean[6];
        setupChambers();
    }

    private void setupChambers() {
        int bulletPosition = new Random().nextInt(6);
        chambers[bulletPosition] = true;
        currentPosition = 0;
    }

    public boolean pullTrigger() {
        return chambers[currentPosition++];
    }

    private static final ItemStack itemStack = ItemBuilder.create().setType(Material.DIAMOND_HOE).setName("Russian Roulette Pistol").build();
    public static ItemStack getItemStack() {
        return itemStack.clone();
    }
}