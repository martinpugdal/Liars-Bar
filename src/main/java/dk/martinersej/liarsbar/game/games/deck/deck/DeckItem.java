package dk.martinersej.liarsbar.game.games.deck.deck;

import dk.martinersej.liarsbar.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum DeckItem {

    JOKER(ItemBuilder.create().setType(Material.PAPER).setName("Joker").build()),
    ACE(ItemBuilder.create().setType(Material.PAPER).setName("Ace").build()),
    QUEEN(ItemBuilder.create().setType(Material.PAPER).setName("Queen").build()),
    KING(ItemBuilder.create().setType(Material.PAPER).setName("King").build()),

    BLANK(ItemBuilder.create().setType(Material.PAPER).setName("Blank").build()); // hidden card (back side)

    private final ItemStack itemStack;

    DeckItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }
}
