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
 * @param <P>
 */
public class BaseChestGUI<P extends GUIPane> extends BaseCanvasGUI<P> implements Sizeable {

    @Setter
    private int maxHeight;
    @Setter
    private int minHeight;

    public BaseChestGUI(Plugin plugin, TextComponent title, int minHeight, int maxHeight, boolean registerDefaultHandlers) {
        super(plugin, title, InventoryType.CHEST, registerDefaultHandlers);
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
    }

    @Override
    public void update() {
        Vector2d oldSize = this.canvas.getSize();
        this.canvas.updateSizeRecursive(this.getMaxSize());
        Vector2d newSize = this.canvas.getSize();
        if (!oldSize.equals(newSize)) {
            this.reloadViews();
        }
        super.update();
    }

    @Override
    public Vector2d getMaxSize() {
        return new Vector2d(9, this.maxHeight);
    }

    @Override
    public Vector2d getMinSize() {
        return new Vector2d(9, this.minHeight);
    }
}
