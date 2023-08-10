package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.builder.gui.ChestGUIBuilder;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Used for creating ChestGUIs. They auto-resize depending on the size of the canvas.
 * The canvas is a SimpleGUIPane which auto-resizes too.
 * If you don't want the gui to resize you should consider setting the minHeight
 * equals the maxHeight or use {@link CanvasGUI}
 * <p></p>
 * Builder: {@link ChestGUIBuilder}
 */
@Getter
public class ChestGUI extends BaseCanvasGUI<SimpleGUIPane> implements Sizeable {
    @Setter
    private int maxHeight;
    @Setter
    private int minHeight;

    public ChestGUI(Plugin plugin, TextComponent title, int minHeight, int maxHeight) {
        super(plugin, title, InventoryType.CHEST);
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.setCanvas(new SimpleGUIPane(this, this.getMinSize(), this.getMaxSize(), true));
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
