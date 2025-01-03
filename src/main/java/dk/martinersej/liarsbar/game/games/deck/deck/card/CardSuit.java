package dk.martinersej.liarsbar.game.games.deck.deck.card;

public enum CardSuit {
    SPADES,
    HEARTS,
    DIAMONDS,
    CLUBS;

    public static CardSuit randomSuit() {
        return values()[(int) (Math.random() * values().length)];
    }
}