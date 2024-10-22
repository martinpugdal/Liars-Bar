package dk.martinersej.liarsbar.game.games.deck.deck;

import dk.martinersej.liarsbar.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DeckItem {

    // Card suits and values
    SPADES_ACE(CardSuit.SPADES, CardValue.ACE, ItemBuilder.create().setType(Material.PAPER).setName("Ace of Spades").build()),
    SPADES_QUEEN(CardSuit.SPADES, CardValue.QUEEN, ItemBuilder.create().setType(Material.PAPER).setName("Queen of Spades").build()),
    SPADES_KING(CardSuit.SPADES, CardValue.KING, ItemBuilder.create().setType(Material.PAPER).setName("King of Spades").build()),

    HEARTS_ACE(CardSuit.HEARTS, CardValue.ACE, ItemBuilder.create().setType(Material.PAPER).setName("Ace of Hearts").build()),
    HEARTS_QUEEN(CardSuit.HEARTS, CardValue.QUEEN, ItemBuilder.create().setType(Material.PAPER).setName("Queen of Hearts").build()),
    HEARTS_KING(CardSuit.HEARTS, CardValue.KING, ItemBuilder.create().setType(Material.PAPER).setName("King of Hearts").build()),

    DIAMONDS_ACE(CardSuit.DIAMONDS, CardValue.ACE, ItemBuilder.create().setType(Material.PAPER).setName("Ace of Diamonds").build()),
    DIAMONDS_QUEEN(CardSuit.DIAMONDS, CardValue.QUEEN, ItemBuilder.create().setType(Material.PAPER).setName("Queen of Diamonds").build()),
    DIAMONDS_KING(CardSuit.DIAMONDS, CardValue.KING, ItemBuilder.create().setType(Material.PAPER).setName("King of Diamonds").build()),

    CLUBS_ACE(CardSuit.CLUBS, CardValue.ACE, ItemBuilder.create().setType(Material.PAPER).setName("Ace of Clubs").build()),
    CLUBS_QUEEN(CardSuit.CLUBS, CardValue.QUEEN, ItemBuilder.create().setType(Material.PAPER).setName("Queen of Clubs").build()),
    CLUBS_KING(CardSuit.CLUBS, CardValue.KING, ItemBuilder.create().setType(Material.PAPER).setName("King of Clubs").build()),

    RED_JOKER(null, CardValue.JOKER, ItemBuilder.create().setType(Material.PAPER).setName("Red Joker").build()),
    BLACK_JOKER(null, CardValue.JOKER, ItemBuilder.create().setType(Material.PAPER).setName("Black Joker").build()),

    RED_BACKSIDE(null, null, ItemBuilder.create().setType(Material.PAPER).setName("Blank").build()), // hidden card (back side)
    BLUE_BACKSIDE(null, null, ItemBuilder.create().setType(Material.PAPER).setName("Blank").build()); // hidden card (back side)

    private final CardSuit cardSuit;
    private final CardValue cardValue;
    private final ItemStack itemStack;

    DeckItem(CardSuit cardSuit, CardValue cardValue, ItemStack itemStack) {
        this.cardSuit = cardSuit;
        this.cardValue = cardValue;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    public static List<DeckItem> getSuit(CardSuit cardSuit) {
        return Arrays.stream(values()).filter(deckItem -> deckItem.getCardSuit() == cardSuit).collect(Collectors.toList());
    }

    public static List<DeckItem> getValue(CardValue cardValue) {
        return Arrays.stream(values()).filter(deckItem -> deckItem.getCardValue() == cardValue).collect(Collectors.toList());
    }

    public static DeckItem getCard(CardSuit cardSuit, CardValue cardValue) {
        return Arrays.stream(values()).filter(deckItem -> deckItem.getCardSuit() == cardSuit && deckItem.getCardValue() == cardValue).findAny().orElse(null);
    }
}
