package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import de.placeblock.betterinventories.gui.GUI;
import io.schark.design.texts.Texts;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SwitchGUIButton extends GUIButton {
    private final GUI targetGUI;

    public SwitchGUIButton(GUI gui, GUI targetGUI) {
        super(gui, new ItemBuilder(Texts.BUTTON_BACK_ITEM, Material.RED_STAINED_GLASS_PANE).build());
        this.targetGUI = targetGUI;
    }

    @Override
    public void onClick(Player player, int slot) {
        this.targetGUI.showPlayer(player);
    }
}
