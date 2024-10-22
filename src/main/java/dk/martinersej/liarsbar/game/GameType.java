package dk.martinersej.liarsbar.game;

import dk.martinersej.liarsbar.game.games.deck.DeckGame;

public enum GameType {
    LIAR_DECK(DeckGame.class);

    private final Class<? extends GameController> clazz;

    GameType(Class<? extends GameController> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends GameController> getClazz() {
        return clazz;
    }
}

