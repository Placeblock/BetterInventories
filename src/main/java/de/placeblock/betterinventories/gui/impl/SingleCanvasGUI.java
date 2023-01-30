package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.util.InventoryTypeMapper;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Author: Placeblock
 */
public class SingleCanvasGUI extends CanvasGUI<SimpleGUIPane> {
    public SingleCanvasGUI(Plugin plugin, TextComponent title, int height) {
        super(plugin, title, InventoryType.CHEST, new Vector2d(9, height));
    }

    public SingleCanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        super(plugin, title, type, InventoryTypeMapper.getSize(type));
    }

    @Override
    public int getSize() {
        return this.canvas.getSlots();
    }

    @Override
    public List<ItemStack> renderContent() {
        return this.canvas.render();
    }

    @Override
    public GUISection getClickedSection(int slot) {
        return this.canvas.getSectionAt(slot);
    }

    @Override
    SimpleGUIPane createCanvas(Vector2d size) {
        return new SimpleGUIPane(this, size);
    }
}
