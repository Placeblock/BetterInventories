package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;


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
    public int getSlots() {
        return this.canvas.getSlots();
    }

    @Override
    public Inventory createBukkitInventory() {
        if (this.getType() == InventoryType.CHEST) {
            int width = (int) (Math.ceil(this.getSize().getX()/9F)*9);
            int size = width*this.getSize().getY();
            return Bukkit.createInventory(null, size, this.getTitle());
        } else {
            return Bukkit.createInventory(null, this.getType(), this.getTitle());
        }
    }

    public Vector2d getSize() {
        return this.canvas.getSize();
    }

    @Override
    protected List<ItemStack> renderContent() {
        return this.canvas.render();
    }

    @Override
    public GUISection getClickedSection(int slot) {
        return this.canvas.getSectionAt(slot);
    }
}
