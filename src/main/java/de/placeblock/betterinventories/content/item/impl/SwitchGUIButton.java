package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;


@SuppressWarnings("unused")
public class SwitchGUIButton extends GUIButton {
    private final Supplier<GUI> targetGUI;

    public SwitchGUIButton(GUI gui, ItemStack item, Supplier<GUI> targetGUI) {
        super(gui, item, targetGUI == null ? null : Sound.UI_BUTTON_CLICK);
        this.targetGUI = targetGUI;
    }

    @Override
    public void onClick(Player player, int slot) {
        if (this.targetGUI != null) {
            this.targetGUI.get().showPlayer(player);
        }
    }
}
