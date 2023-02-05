package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.util.ItemBuilder;
import de.placeblock.betterinventories.gui.GUI;
import io.schark.design.texts.Texts;
import org.bukkit.Material;

import java.util.function.Supplier;


public class BackGUIButton extends SwitchGUIButton {
    public BackGUIButton(GUI gui, Supplier<GUI> targetGUI) {
        super(gui, new ItemBuilder(Texts.BUTTON_BACK_ITEM, Material.RED_STAINED_GLASS_PANE).build(), targetGUI);
    }
}
