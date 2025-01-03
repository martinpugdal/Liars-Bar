package dk.martinersej.liarsbar.lobby;

import dk.martinersej.liarsbar.utils.ItemBuilder;
import dk.martinersej.liarsbar.utils.gui.Anchor;
import dk.martinersej.liarsbar.utils.gui.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class SelectGameGUI extends PaginatedGui {

    public SelectGameGUI() {
        super("Vælg spil", 5, 3 * 9);

        // Set anchor to center the content
        setAnchor(Anchor.CENTER); // Change to CENTER for better visibility

        AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < 4; i++) {
            ItemStack item = new ItemBuilder(Material.EGG)
                .setDisplayName("§a§lLiar's Bar " + counter.incrementAndGet())
                .setLore("§7Klik for at spille Liar's Bar")
                .build();
            addItem(item); // Add item to pageItems
        }

        ItemBuilder builder = new ItemBuilder(Material.STAINED_GLASS_PANE);
        builder.setDisplayName(" ");

        setBorder(builder.build());
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
