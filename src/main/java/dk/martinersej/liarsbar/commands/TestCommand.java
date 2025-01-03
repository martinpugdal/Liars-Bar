package dk.martinersej.liarsbar.commands;

import dk.martinersej.liarsbar.game.GameArea;
import dk.martinersej.liarsbar.game.GameWorld;
import dk.martinersej.liarsbar.game.games.deck.DeckGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        GameArea gameArea = new GameArea(GameWorld.getInstance().getNextLocation());
        Player player = (Player) commandSender;
        player.teleport(gameArea.getTableCenter());

        DeckGame deckGame = new DeckGame();
        deckGame.setupGame();
        deckGame.setupRound();
        deckGame.startRound();

        deckGame.liarCheck();

        return true;
    }
}
