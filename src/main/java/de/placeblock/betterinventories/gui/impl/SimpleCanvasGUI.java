package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.util.InventoryTypeMapper;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
public class SimpleCanvasGUI extends CanvasGUI<SimpleGUIPane> {
    public SimpleCanvasGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, InventoryType.CHEST, new Vector2d(9, height));
    }

    public SimpleCanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        this(plugin, title, type, InventoryTypeMapper.getSize(type));
    }

    protected SimpleCanvasGUI(Plugin plugin, TextComponent title, InventoryType type, Vector2d size) {
        super(plugin, title, type);
        this.setCanvas(new SimpleGUIPane(this, size));
    }
}
