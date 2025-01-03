package dk.martinersej.liarsbar.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.Supplier;

public abstract class PaginatedGui extends BaseGui {

    private final List<ItemStack> pageItems = new ArrayList<>();
    private final Map<Integer, ItemStack> currentPage;
    private final List<Integer> updateTaskIds = new ArrayList<>();
    private int pageSize;
    private int pageNum = 1;
    private Anchor anchor = Anchor.TOP_LEFT; // just default page anchor

    public PaginatedGui(final String title, final int rows, final int pageSize) {
        super(title, rows);
        this.pageSize = pageSize > 0 ? pageSize : rows * 9; // Ensure pageSize is valid
        int inventorySize = rows * 9;
        this.currentPage = new LinkedHashMap<>(inventorySize);
    }

    public PaginatedGui(final String title, final int rows) {
        this(title, rows, 0);
    }

    public int getPageSize() {
        return pageSize;
    }

    public PaginatedGui setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public void addItem(final ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() != Material.AIR) {
            pageItems.add(itemStack);
            Bukkit.getLogger().info("Item added: " + itemStack.getType());
        } else {
            Bukkit.getLogger().warning("Attempted to add null or AIR item.");
        }
    }

    public void addUpdatingItem(Supplier<ItemStack> supplier) {
        final ItemStack[] item = {supplier.get()};
        addItem(item[0]);

        int taskId = new BukkitRunnable() {
            @Override
            public void run() {
                if (getInventory().getViewers().isEmpty()) {
                    updateTaskIds.remove((Integer) this.getTaskId()); // remove task id (Object) from list
                    cancel();
                    return;
                }

                ItemStack newItem = supplier.get();
                item[0].setType(newItem.getType());
                item[0].setItemMeta(newItem.getItemMeta());
                item[0].setAmount(newItem.getAmount());
                item[0].setDurability(newItem.getDurability());
                item[0].setData(newItem.getData());
                for (Map.Entry<Enchantment, Integer> entry : item[0].getEnchantments().entrySet()) {
                    item[0].removeEnchantment(entry.getKey());
                }
                item[0].addEnchantments(newItem.getEnchantments());

                // update once, so it's not for each updating item we will rerender
                if (this.getTaskId() == updateTaskIds.get(0)) {
                    rerenderPage();
                    Bukkit.broadcastMessage("Rerendering");
                }
            }
        }.runTaskTimer(getPlugin(), 0L, 1L).getTaskId();

        updateTaskIds.add(taskId);
    }

    public void removeItem(final ItemStack itemStack) {
        pageItems.remove(itemStack);
    }

    public void removeItem(final int index) {
        pageItems.remove(index);
    }

    ItemStack getPageItem(final int slot) {
        return currentPage.get(slot);
    }

    public void addItem(final ItemStack itemStack, boolean clone) {
        if (clone) {
            addItem(itemStack.clone());
        } else {
            addItem(itemStack);
        }
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public Map<Integer, ItemStack> getCurrentPageItems() {
        return Collections.unmodifiableMap(currentPage);
    }

    public List<ItemStack> getPageItems() {
        return pageItems;
    }

    private List<ItemStack> getPageItems(final int givenPage) {
        final int page = givenPage - 1;

        final List<ItemStack> guiItems = new ArrayList<>();

        int max = ((page * pageSize) + pageSize);
        if (max > pageItems.size()) max = pageItems.size();

        for (int i = page * pageSize; i < max; i++) {
            guiItems.add(pageItems.get(i));
        }

        return guiItems;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalPagesNum() {
        return (int) Math.ceil((double) pageItems.size() / pageSize);
    }

    public int getNextPageNum() {
        if (pageNum + 1 > getTotalPagesNum()) return pageNum;
        return pageNum + 1;
    }

    public int getPreviousPageNum() {
        if (pageNum - 1 == 0) return pageNum;
        return pageNum - 1;
    }

    public boolean nextPage() {
        if (pageNum + 1 > getTotalPagesNum()) return false;
        pageNum++;
        updatePage();
        return true;
    }

    public boolean previousPage() {
        if (pageNum - 1 < 1) return false;
        pageNum--;
        updatePage();
        return true;
    }

    private void populatePage() {
        if (getPageItems(pageNum).size() < pageSize) {
            populatePageByAnchor();
        } else {
            fillPageItems(getPageItems(pageNum));
        }
    }

    private void fillPageItems(List<ItemStack> items) {
        List<Integer> availableSlots = getAvailableSlots();
        Bukkit.getLogger().info("Available Slots: " + availableSlots);
        for (ItemStack itemStack : items) {
            if (availableSlots.isEmpty()) break;
            int slot = availableSlots.remove(0);
            currentPage.put(slot, itemStack);
            getInventory().setItem(slot, itemStack);
        }
    }

    private List<Integer> getAvailableSlots() {
        List<Integer> slots = new ArrayList<>();
        for (int slot = 0; slot < getRows() * 9; slot++) {
            if (getItem(slot) == null && getInventory().getItem(slot) == null) {
                slots.add(slot);
            }
        }
        return slots;
    }

    private void populatePageByAnchor() {
        List<ItemStack> items = getPageItems(pageNum);
        anchor.fill(items, pageSize);
        for (int i = 0; i < items.size(); i++) {
            Bukkit.getLogger().info( "Index: " + i + " Item: " + items.get(i).getType());
        }
        fillPageItems(items);
    }

    @Override
    public void rerender() {
        getInventory().clear();
        currentPage.clear();

        populateGui();

        if (pageSize == 0) pageSize = calculatePageSize();

        updatePage();

        for (HumanEntity viewer : new ArrayList<>(getInventory().getViewers())) ((Player) viewer).updateInventory();
    }

    public abstract void onInventoryClick(InventoryClickEvent event);

    void updatePage() {
        clearPage();
        populatePage();
    }

    void clearPage() {
        for (Map.Entry<Integer, ItemStack> entry : currentPage.entrySet()) {
            getInventory().setItem(entry.getKey(), null);
        }
    }

    public void clearPageItems(final boolean update) {
        pageItems.clear();
        if (update) rerender();
    }

    public void clearPageItems() {
        clearPageItems(false);
    }

    void rerenderPage() {
        clearPage();
        populatePage();
    }

    @Override
    public void open(Player player) {
        super.rerender();
        rerender();
        player.openInventory(getInventory());
        Bukkit.getLogger().info("Inventory opened for player: " + player.getName());
    }

    int calculatePageSize() {
        int counter = 0;
        for (int slot = 0; slot < getRows() * 9; slot++) {
            if (getInventory().getItem(slot) == null && getItem(slot) == null) counter++;
        }
        return counter;
    }
}
