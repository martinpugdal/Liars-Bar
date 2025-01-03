package dk.martinersej.liarsbar.game.games.deck.deck.card;

import dk.martinersej.liarsbar.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CardItem {

    // Card suits and values
    SPADES_ACE(CardSuit.SPADES, CardValue.ACE, ItemBuilder.create().setType(Material.PRISMARINE_SHARD).setName("Ace of Spades").build()),
    SPADES_QUEEN(CardSuit.SPADES, CardValue.QUEEN, ItemBuilder.create().setType(Material.CLAY).setName("Queen of Spades").build()),
    SPADES_KING(CardSuit.SPADES, CardValue.KING, ItemBuilder.create().setType(Material.BLAZE_POWDER).setName("King of Spades").build()),

    HEARTS_ACE(CardSuit.HEARTS, CardValue.ACE, ItemBuilder.create().setType(Material.RABBIT_FOOT).setName("Ace of Hearts").build()),
    HEARTS_QUEEN(CardSuit.HEARTS, CardValue.QUEEN, ItemBuilder.create().setType(Material.FEATHER).setName("Queen of Hearts").build()),
    HEARTS_KING(CardSuit.HEARTS, CardValue.KING, ItemBuilder.create().setType(Material.BONE).setName("King of Hearts").build()),

    DIAMONDS_ACE(CardSuit.DIAMONDS, CardValue.ACE, ItemBuilder.create().setType(Material.RABBIT_HIDE).setName("Ace of Diamonds").build()),
    DIAMONDS_QUEEN(CardSuit.DIAMONDS, CardValue.QUEEN, ItemBuilder.create().setType(Material.GHAST_TEAR).setName("Queen of Diamonds").build()),
    DIAMONDS_KING(CardSuit.DIAMONDS, CardValue.KING, ItemBuilder.create().setType(Material.CLAY_BRICK).setName("King of Diamonds").build()),

    CLUBS_ACE(CardSuit.CLUBS, CardValue.ACE, ItemBuilder.create().setType(Material.QUARTZ).setName("Ace of Clubs").build()),
    CLUBS_QUEEN(CardSuit.CLUBS, CardValue.QUEEN, ItemBuilder.create().setType(Material.DIAMOND).setName("Queen of Clubs").build()),
    CLUBS_KING(CardSuit.CLUBS, CardValue.KING, ItemBuilder.create().setType(Material.BLAZE_ROD).setName("King of Clubs").build()),

    RED_JOKER(null, CardValue.JOKER, ItemBuilder.create().setType(Material.SLIME_BALL).setName("Red Joker").build()),
    BLUE_JOKER(null, CardValue.JOKER, ItemBuilder.create().setType(Material.FERMENTED_SPIDER_EYE).setName("Black Joker").build()),

    RED_BACKSIDE(null, null, ItemBuilder.create().setType(Material.SUGAR).setName("Blank").build()), // hidden card (back side)
    BLUE_BACKSIDE(null, null, ItemBuilder.create().setType(Material.WHEAT).setName("Blank").build()); // hidden card (back side)

    private final CardSuit cardSuit;
    private final CardValue cardValue;
    private final ItemStack itemStack;

    CardItem(CardSuit cardSuit, CardValue cardValue, ItemStack itemStack) {
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

    public static List<CardItem> getSuit(CardSuit cardSuit) {
        return Arrays.stream(values()).filter(cardItem -> cardItem.getCardSuit() == cardSuit).collect(Collectors.toList());
    }

    public static List<CardItem> getValue(CardValue cardValue) {
        return Arrays.stream(values()).filter(cardItem -> cardItem.getCardValue() == cardValue).collect(Collectors.toList());
    }

    public static CardItem getCard(CardSuit cardSuit, CardValue cardValue) {
        return Arrays.stream(values()).filter(cardItem -> cardItem.getCardSuit() == cardSuit && cardItem.getCardValue() == cardValue).findAny().orElse(null);
    }

    public static CardItem getCardByItemStack(ItemStack itemStack) {
        return Arrays.stream(values()).filter(cardItem -> cardItem.getItemStack().isSimilar(itemStack)).findAny().orElse(null);
    }
}
