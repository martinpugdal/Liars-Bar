package dk.martinersej.liarsbar.game.games.deck;

import dk.martinersej.liarsbar.game.GameController;
import dk.martinersej.liarsbar.game.GameType;
import dk.martinersej.liarsbar.game.games.deck.deck.Deck;

public class DeckGame extends GameController {

    private final Deck deck;

    public DeckGame() {
        super(GameType.LIAR_DECK);

        this.deck = new Deck();
    }

    @Override
    public void startGame() {
        deck.getShuffledCards();
    }

    @Override
    public void endGame() {

    }

}