package dk.martinersej.liarsbar.game.games.deck;

import dk.martinersej.liarsbar.game.Game;
import dk.martinersej.liarsbar.game.GamePlayer;
import dk.martinersej.liarsbar.game.GameType;
import dk.martinersej.liarsbar.game.games.deck.deck.Deck;
import dk.martinersej.liarsbar.game.games.deck.deck.DeckItem;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class DeckGame extends Game {

    private final Deck deck;

    public DeckGame() {
        super(GameType.LIAR_DECK);

        this.deck = new Deck();
    }

    @Override
    public void setupGame() {

    }

    @Override
    public void startGame() {
        deck.getShuffledCards();
    }

    @Override
    public void endGame() {

    }

    public void visualizeHiddenCards(DeckPlayer deckPlayer) {
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(deckPlayer.getPlayer().getEntityId(), 0, CraftItemStack.asNMSCopy(DeckItem.BLANK.getItemStack()));
        for (GamePlayer gameplayer : getPlayers()) {
            if (gameplayer.equals(deckPlayer)) {
                continue;
            }
            Player p = deckPlayer.getPlayer();
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
