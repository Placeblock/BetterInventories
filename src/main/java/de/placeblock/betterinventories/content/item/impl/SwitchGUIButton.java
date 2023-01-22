package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SwitchGUIButton extends GUIButton {
    private final GUI targetGUI;

    public SwitchGUIButton(GUI gui, ItemStack item, GUI targetGUI) {
        super(gui, item);
        this.targetGUI = targetGUI;
    }

    @Override
    public void onClick(Player player) {
        this.targetGUI.showPlayer(player);
    }
}
