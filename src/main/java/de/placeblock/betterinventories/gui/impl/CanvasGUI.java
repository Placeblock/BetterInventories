package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.gui.CanvasGUIBuilder;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.util.InventoryTypeMapper;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Used for creating GUIs that only have one canvas e.g. HopperGUIs and a fixed size.
 * Bear in mind that for creating Chest Inventories you should use {@link ChestGUI}
 * <p></p>
 * Builder: {@link CanvasGUIBuilder}
 */
public class CanvasGUI extends BaseCanvasGUI<SimpleGUIPane> {
    public CanvasGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, InventoryType.CHEST, new Vector2d(9, height));
    }

    public CanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        this(plugin, title, type, InventoryTypeMapper.getSize(type));
    }

    protected CanvasGUI(Plugin plugin, TextComponent title, InventoryType type, Vector2d size) {
        super(plugin, title, type);
        this.setCanvas(new SimpleGUIPane(this, size, size, false));
    }
}
