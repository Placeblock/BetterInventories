package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Used for creating ChestGUIs. They auto-resize depending on the size of the canvas.
 * If you don't want the gui to resize you should consider setting the minHeight
 * equals the maxHeight or use {@link CanvasGUI}
 * To instantiate use {@link ChestGUI}
 * @param <P> The type of the main canvas
 */
public class BaseChestGUI<P extends GUIPane> extends BaseCanvasGUI<P> implements Sizeable {
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
     * @param preventInteraction Whether to register cancel-interaction handler
     */
    public BaseChestGUI(Plugin plugin, TextComponent title, int minHeight, int maxHeight, boolean preventInteraction) {
        super(plugin, title, InventoryType.CHEST, preventInteraction);
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
}
