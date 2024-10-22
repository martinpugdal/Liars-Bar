package dk.martinersej.liarsbar.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerJoinListener implements Listener {

    private final String RESOURCE_PACK_URL = "https://drive.google.com/uc?export=download&id=1MyAYR_DOlh_v-8WBrxF8bgqp1hoUJ9vw";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("§eVi anbefaler at du bruger vores texture pack for at få den bedste oplevelse!");
        event.getPlayer().setResourcePack(RESOURCE_PACK_URL);
    }

    @EventHandler
    public void onResourcepackStatusEvent(PlayerResourcePackStatusEvent event) {
        if (event.getStatus() != PlayerResourcePackStatusEvent.Status.ACCEPTED) {
            event.getPlayer().setResourcePack(RESOURCE_PACK_URL);
        }
    }
}
