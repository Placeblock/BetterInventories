package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@Getter
public abstract class CanvasGUI<C extends GUIPane> extends GUI {
    protected C canvas;

    protected CanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        super(plugin, title, type);
    }

    protected void setCanvas(C canvas) {
        this.canvas = canvas;
    }
}
