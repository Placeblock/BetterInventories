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

    public abstract void onClick(Player player);
    public void onLeftClick(Player player) {}
    public void onRightClick(Player player) {}
    public void onShiftClick(Player player) {
        this.onClick(player);
    }
    public void onShiftLeftClick(Player player) {
        this.onLeftClick(player);
    }
    public void onShiftRightClick(Player player) {
        this.onRightClick(player);
    }

}
