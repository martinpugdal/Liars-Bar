package dk.martinersej.liarsbar.listeners;

import dk.martinersej.liarsbar.LiarsBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerJoinListener implements Listener {

    private final String RESOURCE_PACK_URL = "https://drive.google.com/uc?export=download&id=1MyAYR_DOlh_v-8WBrxF8bgqp1hoUJ9vw";

    private void setResourcePack(Player player) {
        Bukkit.getScheduler().runTaskLater(
            LiarsBar.get(),
            () -> player.setResourcePack(RESOURCE_PACK_URL), 20L
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§eVi anbefaler at du bruger vores texture pack for at få den bedste oplevelse!");
        setResourcePack(player);
    }

    @EventHandler
    public void onResourcepackStatusEvent(PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            setResourcePack(event.getPlayer());
        }
    }
}
