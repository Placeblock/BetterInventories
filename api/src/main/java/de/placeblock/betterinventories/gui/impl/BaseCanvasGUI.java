package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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
     * @param removeItems Whether to remove loose items on close.
     *                   The first player that closes the gui gets the items
     */
    protected BaseCanvasGUI(Plugin plugin, TextComponent title, InventoryType type, boolean removeItems) {
        super(plugin, title, type, removeItems);
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
     * Searches the GUI recursively. The SearchData is filled recursively.
     * @param searchData The searchData that contains all needed information
     */
    @Override
    public void searchSection(SearchData searchData) {
        Vector2d relativePos = this.canvas.slotToVector(searchData.getSlot());
        searchData.setRelativePos(relativePos);
        this.canvas.search(searchData);
    }

    @Override
    public void provideItem(ItemStack item) {
        this.canvas.provideItem(item);
    }

    /**
     * @return The main canvas
     */
    @SuppressWarnings("unused")
    public C getCanvas() {
        return this.canvas;
    }

    /**
     * Builder for creating BaseCanvasGUIs
     * @param <B> The Builder that implements this one
     * @param <G> The GUI that is built
     * @param <C> The Pane that lives inside the ChestGUI
     * @param <P> The Plugin that uses this builder
     */
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, G, C, P>, G extends BaseCanvasGUI<C>, C extends GUIPane, P extends JavaPlugin> extends GUI.Builder<B, G, P> {
        /**
         * Creates a new Builder
         * @param plugin The plugin that uses this builder
         */
        public AbstractBuilder(P plugin) {
            super(plugin);
        }
    }

}
