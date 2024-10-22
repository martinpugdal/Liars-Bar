package dk.martinersej.liarsbar.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class GamemodeCommand implements CommandExecutor {

    public GamemodeCommand() {
        List<String> numbers = new java.util.ArrayList<>();
        numbers.add("0");
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        Bukkit.getPluginCommand("gamemode").setAliases(Collections.singletonList("gm"));
        Bukkit.getPluginCommand("gamemode").setPermission("liarsbar.gamemode");
        Bukkit.getPluginCommand("gamemode").setTabCompleter((sender, command, alias, args) -> numbers);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("§cBrug: /gamemode <0|1|2|3> [spiller]");
            return true;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cDu skal være en spiller for at bruge denne kommando!");
            return true;
        }


        GameMode gameMode;
        switch (strings[0]) {
            case "0":
                gameMode = GameMode.SURVIVAL;
                commandSender.sendMessage("§aGamemode sat til overlevelse!");
                break;
            case "1":
                gameMode = GameMode.CREATIVE;
                commandSender.sendMessage("§aGamemode sat til kreativ!");
                break;
            case "2":
                gameMode = GameMode.ADVENTURE;
                commandSender.sendMessage("§aGamemode sat til eventyr!");
                break;
            case "3":
                gameMode = GameMode.SPECTATOR;
                commandSender.sendMessage("§aGamemode sat til spectator!");
                break;
            default:
                commandSender.sendMessage("§cBrug: /gamemode <0|1|2|3> [spiller]");
                return true;
        }

        Player player = (Player) commandSender;
        player.setGameMode(gameMode);
        return true;
    }
}
