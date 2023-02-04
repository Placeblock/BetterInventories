package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
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
public abstract class BaseCanvasGUI<C extends GUIPane> extends GUI {
    protected C canvas;

    protected BaseCanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        super(plugin, title, type);
    }

    protected void setCanvas(C canvas) {
        this.canvas = canvas;
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
    public void prerenderContent() {
        this.canvas.prerender();
    }

    @Override
    public GUISection getClickedSection(int slot) {
        return this.canvas.getSectionAt(slot);
    }
}
