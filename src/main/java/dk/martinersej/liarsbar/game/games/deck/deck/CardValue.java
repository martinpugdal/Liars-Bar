package dk.martinersej.liarsbar.game.games.deck.deck;

import org.bukkit.inventory.ItemStack;

public enum CardValue {
    JOKER(DeckItem.JOKER),
    ACE(DeckItem.ACE),
    QUEEN(DeckItem.QUEEN),
    KING(DeckItem.KING);

    private final DeckItem deckItem;

    CardValue(DeckItem deckItem) {
        this.deckItem = deckItem;
    }

    public ItemStack getItemStack() {
        return deckItem.getItemStack();
    }
}