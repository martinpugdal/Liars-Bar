package dk.martinersej.liarsbar.game.games.deck;

import dk.martinersej.liarsbar.LiarsBar;
import dk.martinersej.liarsbar.game.Game;
import dk.martinersej.liarsbar.game.GamePlayer;
import dk.martinersej.liarsbar.game.GameType;
import dk.martinersej.liarsbar.game.games.deck.deck.Deck;
import dk.martinersej.liarsbar.game.games.deck.deck.card.CardArmorstand;
import dk.martinersej.liarsbar.game.games.deck.deck.card.CardItem;
import dk.martinersej.liarsbar.game.games.deck.deck.card.CardSuit;
import dk.martinersej.liarsbar.utils.npc.NPC;
import dk.martinersej.liarsbar.utils.npc.NPCBuilder;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.trait.SleepTrait;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.List;
import java.util.function.Consumer;

public class DeckGame extends Game {

    private final Deck deck;
    private List<CardItem> lastPlayedCards;
    private CardSuit roundSuit;

    public DeckGame() {
        super(GameType.LIAR_DECK);

        this.deck = new Deck();
    }

    @Override
    public void setupGame() {

    }

    @Override
    public void setupRound() {
        roundSuit = CardSuit.randomSuit();
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

        // check if the last played cards are valid
        boolean valid = lastPlayedCards.stream().noneMatch(card -> card.getCardSuit() != roundSuit);

        displayCardsOnTable(() -> {
            if (!valid) {
                // punish the caller
                punishment((DeckPlayer) getCurrentPlayer());
            } else {
                // punish the liar
                punishment((DeckPlayer) getPreviousPlayer());
            }
        });

        // reset the last played cards
        lastPlayedCards.clear();

        // wait for a few seconds before starting a new round
        LiarsBar.get().getServer().getScheduler().runTaskLater(LiarsBar.get(), this::setupRound, 20L);
    }

    private void displayCardsOnTable(Runnable callback) {
        int cards = lastPlayedCards.size();

        // display the cards on the table
        Location tableCenter = getGameArea().getTableCenter().clone();
        for (int i = 0; i < cards; i++) {
            CardItem card = lastPlayedCards.get(i);
            tableCenter.add(0.03, 0, 0);
            CardArmorstand.spawn(tableCenter, card);
        }

        // wait for a few seconds
        callback.run();
    }


    private void punishment(DeckPlayer deckPlayer) {
        boolean killed = deckPlayer.getPistol().pullTrigger();
        Player player = deckPlayer.getPlayer();

        player.getWorld().strikeLightningEffect(player.getLocation());
        if (!killed) {
            return;
        }
        // do the death animation
        player.setHealth(0);

        addSleepingNPC(player); // fake death animation

        deckPlayer.setAlive(false);
    }

    public void visualizeHiddenCards(DeckPlayer deckPlayer) {
        if (deckPlayer.getHand().isEmpty()) { // probably dead or has no cards in hand
            return;
        }

        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(deckPlayer.getPlayer().getEntityId(), 0, CraftItemStack.asNMSCopy(CardItem.RED_BACKSIDE.getItemStack()));
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
