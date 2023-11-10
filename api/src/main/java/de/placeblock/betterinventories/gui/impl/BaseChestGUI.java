package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Used for creating ChestGUIs. They auto-resize depending on the size of the canvas.
 * If you don't want the gui to resize you should consider setting the minHeight
 * equals the maxHeight or use {@link CanvasGUI}
 * To instantiate use {@link ChestGUI}
 * @param <C> The type of the main canvas
 */
public abstract class BaseChestGUI<C extends GUIPane> extends BaseCanvasGUI<C> implements Sizeable {
    /**
     * The maximum height of the GUI
     */
    @Setter
    private int maxHeight;

    /**
     * The minimum height of the GUI
     */
    @Setter
    private int minHeight;

    /**
     * Creates a new BaseChestGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param minHeight The minimum height of the GUI
     * @param maxHeight The maximum height of the GUI
     */
    protected BaseChestGUI(Plugin plugin, TextComponent title, boolean removeItems, int minHeight, int maxHeight) {
        super(plugin, title, InventoryType.CHEST, removeItems);
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
    }

    /**
     * Updates the GUI and reloads all Views if needed
     */
    @Override
    public void update() {
        Vector2d oldSize = this.canvas.getSize();
        this.canvas.updateSizeRecursive(this);
        Vector2d newSize = this.canvas.getSize();
        if (!oldSize.equals(newSize)) {
            this.reloadViews();
        }
        super.update();
    }

    /**
     * @return The maximum size of the GUI
     */
    @Override
    public Vector2d getMaxSize() {
        return new Vector2d(9, this.maxHeight);
    }

    /**
     * @return The minimum size of the GUI
     */
    @Override
    public Vector2d getMinSize() {
        return new Vector2d(9, this.minHeight);
    }

    @SuppressWarnings("UnusedReturnValue")
    @Getter(AccessLevel.PROTECTED)
    public static abstract class Builder<B extends Builder<B, G, C, P>, G extends BaseChestGUI<C>, C extends GUIPane, P extends JavaPlugin> extends BaseCanvasGUI.Builder<B, G, C, P> {
        private int minHeight;
        private int maxHeight;

        public Builder(P plugin) {
            super(plugin);
        }

        public B minHeight(int minHeight) {
            this.minHeight = minHeight;
            return this.self();
        }

        public B maxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this.self();
        }
    }
}
