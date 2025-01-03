package dk.martinersej.liarsbar.lobby;

import dk.martinersej.liarsbar.utils.npc.NPC;
import dk.martinersej.liarsbar.utils.npc.NPCBuilder;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SelectGameNPC extends NPC {

    private final SelectGameGUI selectGameGUI = new SelectGameGUI();

    public SelectGameNPC() {
        super();

        World lobbyWorld = Bukkit.getWorlds().get(0);
        NPCBuilder npcBuilder = NPCBuilder.create().
            withName("Vælg spil").
            withSkinTexture("ewogICJ0aW1lc3RhbXAiIDogMTcyOTgyOTA2NTY4MywKICAicHJvZmlsZUlkIiA6ICJhYWMxYjA2OWNkMjE0NWE2ODNlNzQxNzE4MDcxMGU4MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJqdXNhbXUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTU5NzNhZjY5OTljNjhkNzkzMzQ2YjMwNTAyMTY5ZTdjOWMyYWI4YmQwOTlmOTk2NGZhMWM2NjMyNjUxNGZjMCIKICAgIH0KICB9Cn0=").
            withSkinSignature("kNpXpzZgGO2WAwAtdzZbladfWLnf8feXYLzoiabaoVi7R2cqeQi2v+KZqIJYLYOgKPg7XUhf1re/P/JFWLYD1bdg81PBlW2lrpcDDL9+zLhCYyRGIzf3AXIocX7FMHCx9g6ITNcqEAHbO4DsTARKuG/eT9lNnGigWzhOHREUkAKK4N9brAzMCSH2JjzpaasTc5dTN7KPflmiyqQ58d6BPVBEfuUpIzTMOvBGkW7S2CEsWZYjr5gZX5FTDr3QJ+nzlXGnvZb6J/iOx4z/F6eC5Q080LDv0C/VMUES3AdMvo6CIxV8mMTw57yBeEACPUYnq6ZUcq3IQ1wCLO54KVklL4vYIVN/2FF3ZlRzl8RwKkuIDcP8F8IKybQvxh+stvs7lh50hU6wTAw6nIbKX2V1M7V2Zajk+SbJoX4s0QD7VxXg+MyqxaP6REQSmlNp4wde/cCS67tyxtv+ZNYlJo3CAn36D+NsyoWxLSXU5TiyZzgUeY06TRNcGKBmth4+G+qYYye191Ou+FajaqEbjjq6Fm+sQkDgNBlxfQgEKVDXefNu1kSfbcv98sD4Xvgm0rAGRkl4pGaG89XJE9iEJzVOVs4skd3PO5eHQfTB5ygIyH5XbO3B7d4vMDMZnahQcmwNZ8+xpL5HEXsnz5qj7q1UH3RlKCaC1KzL+RSMWxJ5Du8=").
            withLookClose(true).
            withLocation(new Location(lobbyWorld, -647, 4, -736));
        setNPC(npcBuilder.build());

        spawn();
    }

    @Override
    public void onRightClick(NPCRightClickEvent event) {
        selectGameGUI.open(event.getClicker());
    }

    @Override
    public void onLeftClick(NPCRightClickEvent event) {
    }
}
