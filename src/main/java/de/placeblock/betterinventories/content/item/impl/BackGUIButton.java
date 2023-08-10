package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.util.ItemBuilder;
import de.placeblock.betterinventories.gui.GUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;

import java.util.function.Supplier;

/**
 * A {@link GUIButton} which returns back to an {@link GUI}.
 */
@SuppressWarnings("unused")
public class BackGUIButton extends SwitchGUIButton {
    public BackGUIButton(GUI gui, Supplier<GUI> targetGUI, TextComponent title) {
        super(gui, new ItemBuilder(title, Material.RED_STAINED_GLASS_PANE).build(), targetGUI);
    }

    public BackGUIButton(GUI gui, Supplier<GUI> targetGUI) {
        this(gui, targetGUI, Component.text("Zurück").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
    }
}
