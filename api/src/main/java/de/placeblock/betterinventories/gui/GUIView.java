package de.placeblock.betterinventories.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A GUIView is created for each player who opens an inventory.
 *
 * @param player    The Player of this View
 * @param inventory The Bukkit Inventory of this View
 */
public record GUIView(Player player, Inventory inventory) {
    /**
     * Creates a new GUIView
     *
     * @param player    The Player of this View
     * @param inventory The Bukkit Inventory of this View
     */
    public GUIView(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        this.player.openInventory(inventory);
    }

    /**
     * Gets called when the new rendered content should be showed to the player.
     * Updates only changing items in the player's inventory.
     *
     * @param content The new rendered content. The size of the list should be equals the slots in the inventory.
     */
    public void update(List<ItemStack> content) {
        ItemStack[] contents = this.inventory.getContents();
        for (int i = 0; i < contents.length && i < content.size(); i++) {
            ItemStack newItem = content.get(i);
            ItemStack oldItem = contents[i];
            if (newItem == null || !newItem.equals(oldItem)) {
                this.inventory.setItem(i, newItem);
            }
        }
    }

}
