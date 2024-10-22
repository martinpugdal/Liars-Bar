package dk.martinersej.liarsbar.game.games.deck.deck;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Deck {

    private final Card[] cards;
    private final DeckItem backside;

    public Deck() {
        cards = new Card[20];
        backside = Math.random() < 0.5 ? DeckItem.RED_BACKSIDE : DeckItem.BLUE_BACKSIDE;
    }

    public Card[] getCards() {
        return cards;
    }

    public void shuffle() {
        // shuffle the deck
        // The deck consists of 20 cards: 6 Aces, 6 Kings, 6 Queens, and 2 Jokers
        // randomize the suits.
        List<CardSuit> suits = Arrays.asList(CardSuit.values());
        Random random = new Random();
        suits = suits.stream().sorted((a, b) -> random.nextBoolean() ? -1 : 1).collect(Collectors.toList());
        for (int i = 0; i < 6; i++) {
            cards[i] = new Card(DeckItem.getCard(suits.get(random.nextInt(4)), CardValue.ACE));
            cards[i + 6] = new Card(DeckItem.getCard(suits.get(random.nextInt(4)), CardValue.KING));
            cards[i + 12] = new Card(DeckItem.getCard(suits.get(random.nextInt(4)), CardValue.QUEEN));
        }
        cards[18] = new Card(DeckItem.RED_JOKER);
        cards[19] = new Card(DeckItem.BLACK_JOKER);
    }
}
