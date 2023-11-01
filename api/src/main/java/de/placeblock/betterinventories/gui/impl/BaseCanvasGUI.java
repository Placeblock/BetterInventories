package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * The Base Class for GUIs which only have just one canvas e.g. Chest, Hopper, Crafting...
 * If you want to instantiate this one use {@link CanvasGUI}.
 * @param <C> The type of the Canvas
 */
public abstract class BaseCanvasGUI<C extends GUIPane> extends GUI {
    /**
     * The main canvas to which Sections can be added
     */
    protected C canvas;

    /**
     * Creates a new BaseCanvasGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     */
    protected BaseCanvasGUI(Plugin plugin, TextComponent title, InventoryType type) {
        super(plugin, title, type);
    }

    /**
     * Sets the canvas for this GUI. Should be done before using {@link #getCanvas()}
     * @param canvas The canvas to be set
     */
    protected void setCanvas(C canvas) {
        this.canvas = canvas;
    }

    /**
     * @return The size of this GUI in slots
     */
    @Override
    public int getSlots() {
        return this.canvas.getSlots();
    }

    /**
     * @return A new Bukkit Inventory matching the size or type of this GUI
     */
    @Override
    public Inventory createBukkitInventory() {
        if (this.getType() == InventoryType.CHEST) {
            return Bukkit.createInventory(null, this.getSlots(), this.getTitle());
        } else {
            return Bukkit.createInventory(null, this.getType(), this.getTitle());
        }
    }

    /**
     * @return The Size of this GUI as a Vector
     */
    public Vector2d getSize() {
        return this.canvas.getSize();
    }

    /**
     * @return The rendered representation of the canvas
     */
    @Override
    public List<ItemStack> renderContent() {
        return this.canvas.render();
    }

    /**
     * Uses recursion to find the GUISection which was clicked
     * @param slot The slot that got clicked
     * @return The section which lies at the specific slot, or null if there is no section.
     */
    @Override
    public GUISection.SearchData getClickedSection(int slot) {
        return this.canvas.search(slot);
    }

    /**
     * @return The main canvas
     */
    @SuppressWarnings("unused")
    public C getCanvas() {
        return this.canvas;
    }

}
