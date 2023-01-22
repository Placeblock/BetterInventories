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

    public void onLeftClick(Player player) {}
    public void onRightClick(Player player) {}
    public abstract void onClick(Player player);

}
