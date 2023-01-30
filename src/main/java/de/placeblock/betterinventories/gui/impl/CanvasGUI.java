package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@Getter
public abstract class CanvasGUI<C extends GUIPane> extends GUI {
    protected final C canvas;

    protected CanvasGUI(Plugin plugin, TextComponent title, InventoryType type, Vector2d size) {
        super(plugin, title, type);
        this.canvas = this.createCanvas(size);
    }

    abstract C createCanvas(Vector2d size);
}
