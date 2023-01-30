package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public abstract class GUIButton extends GUIItem {

    public GUIButton(GUI gui, ItemStack item) {
        super(gui, item);
    }

    public abstract void onClick(Player player, int slot);
    public void onLeftClick(Player player, int slot) {}
    public void onRightClick(Player player, int slot) {}
    public void onShiftClick(Player player, int slot) {
        this.onClick(player, slot);
    }
    public void onShiftLeftClick(Player player, int slot) {
        this.onLeftClick(player, slot);
    }
    public void onShiftRightClick(Player player, int slot) {
        this.onRightClick(player, slot);
    }

}
