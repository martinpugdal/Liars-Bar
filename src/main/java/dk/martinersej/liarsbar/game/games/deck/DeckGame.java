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
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.List;

public class DeckGame extends Game {

    private final Deck deck;
    private List<DeckItem> lastPlayedCards;

    public DeckGame() {
        super(GameType.LIAR_DECK);

        this.deck = new Deck();
    }

    @Override
    public void setupGame() {
    }

    @Override
    public void setupRound() {

    }

    @Override
    public void startRound() {
        deck.shuffle();
    }

    @Override
    public void endRound() {

    }

    @Override
    public void liarCheck() {
        // check if the last played cards are valid and if the player is lying
        // if lying, the liar will be punished
        // if not lying, the caller will be punished
    }

    private void punishment(GamePlayer gamePlayer) {
        // punish the player
    }

    public void visualizeHiddenCards(DeckPlayer deckPlayer) {
        if (deckPlayer.getHand().isEmpty()) { // probably dead or has no cards in hand
            return;
        }

        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(deckPlayer.getPlayer().getEntityId(), 0, CraftItemStack.asNMSCopy(DeckItem.RED_BACKSIDE.getItemStack()));
        for (GamePlayer gameplayer : getPlayers()) {
            if (gameplayer.equals(deckPlayer)) {
                continue;
            }
            Player p = deckPlayer.getPlayer();
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @EventHandler
    public void onHotBarChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        DeckPlayer deckPlayer = (DeckPlayer) getGamePlayer(player);
        if (deckPlayer == null) {
            return;
        }
        visualizeHiddenCards(deckPlayer);
    }
}
