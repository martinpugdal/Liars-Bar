package dk.martinersej.liarsbar.game.games.deck.deck;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    private final Card[] cards;

    public Deck() {
        cards = new Card[20];
        // The deck consists of 20 cards: 6 Aces, 6 Kings, 6 Queens, and 2 Jokers
        int cardAmount = 6;
        for (int i = 0; i < cardAmount; i++) {
            cards[i] = new Card(CardValue.ACE);
            cards[i + cardAmount] = new Card(CardValue.KING);
            cards[i + cardAmount*2] = new Card(CardValue.QUEEN);

        }
        int jokerAmount = 2;
        for (int i = 0; i < jokerAmount; i++) {
            cards[cards.length-i-1] = new Card(CardValue.JOKER); // i-th last card
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public List<Card> getShuffledCards() {
        List<Card> shuffledCards = Arrays.stream(cards).collect(Collectors.toList());
        java.util.Collections.shuffle(shuffledCards);
        return shuffledCards;
    }
}
