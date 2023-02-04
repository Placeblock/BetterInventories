package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SwitchGUIButton extends GUIButton {
    private final Supplier<GUI> targetGUI;

    public SwitchGUIButton(GUI gui, ItemStack item, Supplier<GUI> targetGUI) {
        super(gui, item);
        this.targetGUI = targetGUI;
    }

    @Override
    public void onClick(Player player, int slot) {
        this.targetGUI.get().showPlayer(player);
    }
}
