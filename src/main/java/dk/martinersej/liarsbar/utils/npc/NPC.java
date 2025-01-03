package dk.martinersej.liarsbar.utils.npc;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class NPC implements Listener {

    private net.citizensnpcs.api.npc.NPC npc;

    public NPC(net.citizensnpcs.api.npc.NPC npc) {
        this.npc = npc;
    }

    public NPC() {
    }

    public void spawn() {
        spawn(null);
    }

    public void spawn(Location location) {
        if (npc == null || npc.isSpawned()) return;
        npc.spawn(location == null ? npc.getStoredLocation() : location);
        Bukkit.getServer().getPluginManager().registerEvents(this, JavaPlugin.getProvidingPlugin(getClass()));
    }

    public void despawn() {
        if (npc == null) return;
        npc.despawn();
        HandlerList.unregisterAll(this);
    }

    public abstract void onRightClick(NPCRightClickEvent event);

    public abstract void onLeftClick(NPCRightClickEvent event);

    public net.citizensnpcs.api.npc.NPC getNPC() {
        return npc;
    }

    public void setNPC(net.citizensnpcs.api.npc.NPC npc) {
        this.npc = npc;
    }

    @EventHandler
    public void onNpcRightClick(NPCRightClickEvent event) {
        if (!event.getNPC().equals(npc)) return;
        onRightClick(event);
    }

    @EventHandler
    public void onNpcLeftClick(NPCRightClickEvent event) {
        if (!event.getNPC().equals(npc)) return;
        onLeftClick(event);
    }
}
