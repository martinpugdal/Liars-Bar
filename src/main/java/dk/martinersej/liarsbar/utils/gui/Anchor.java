package dk.martinersej.liarsbar.utils.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Anchor {
    TOP,
    CENTER,
    BOTTOM,
    LEFT,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    private static final int COLUMN_SIZE = 9;

    private boolean isInBounds(int index, int pageSize) {
        return index >= 0 && index < pageSize;
    }

    public void fill(List<ItemStack> list, int pageSize) {
        List<ItemStack> sortedList = new ArrayList<>(Collections.nCopies(pageSize, new ItemStack(Material.AIR)));
        int numElements = list.size();

        final int rows = (int) Math.ceil((double) pageSize / COLUMN_SIZE);

        int startIndex = 0;
        switch (this) {
            case TOP:
            case BOTTOM:
            case TOP_LEFT:
            case TOP_RIGHT:
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
            case LEFT:
            case RIGHT:
            case CENTER:
                //steps:
                // 1. get the middle of the pagesize
                // 2. get the middle of the current list
                // 3. calculate the start index for the list by subtracting the middle of the list from the middle of the pagesize
                // 4. if the pagesize is even, add 1 to start index
                // 5. iterate through the list and add the elements to the sorted list
                int middle = pageSize / 2;
                int middleList = numElements / 2;
                startIndex = middle - middleList;
                if (pageSize % 2 == 0) {
                    startIndex++;
                }
                break;
        }

        // Fill the sorted list with the elements from the original list
        for (int i = 0; i < numElements; i++) {
            sortedList.set(startIndex + i, list.get(i));
        }

        list.clear();
        list.addAll(sortedList);
    }
}