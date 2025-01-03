package dk.martinersej.liarsbar.game.games.deck;

import dk.martinersej.liarsbar.game.GamePlayer;
import dk.martinersej.liarsbar.game.games.deck.deck.card.Card;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DeckPlayer extends GamePlayer {

    private final List<Card> hand = new ArrayList<>();
    private RussianRoulettePistol pistol;

    public DeckPlayer(Player player) {
        super(player);
        pistol = new RussianRoulettePistol();
    }



    public List<Card> getHand() {
        return hand;
    }

    public RussianRoulettePistol getPistol() {
        return pistol;
    }
}
