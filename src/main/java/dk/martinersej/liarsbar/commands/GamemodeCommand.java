package dk.martinersej.liarsbar.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        return false;
    }
}
