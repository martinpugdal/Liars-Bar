package dk.martinersej.liarsbar.game;

import dk.martinersej.liarsbar.game.games.deck.DeckGame;

public enum GameType {
    LIAR_DECK(DeckGame.class);

    private final Class<? extends Game> clazz;

    GameType(Class<? extends Game> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Game> getClazz() {
        return clazz;
    }
}

