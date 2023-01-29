package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.InventoryTypeMapper;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Author: Placeblock
 */
@Getter
public class SingleCanvasGUI extends GUI {
    protected final SimpleGUIPane canvas;

    public SingleCanvasGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, InventoryType.CHEST, new Vector2d(9, height));
    }

    public SingleCanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        this(plugin, title, type, InventoryTypeMapper.getSize(type));
    }

    private SingleCanvasGUI(Plugin plugin, TextComponent title, InventoryType type, Vector2d size) {
        super(plugin, title, type);
        System.out.println(size);
        this.canvas = new SimpleGUIPane(this, size);
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
}
