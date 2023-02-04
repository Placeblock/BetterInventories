package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
public abstract class ChestCanvasGUI<P extends GUIPane> extends CanvasGUI<P> {
    private int lastUpdateRows = 1;

    public ChestCanvasGUI(Plugin plugin, TextComponent title) {
        super(plugin, title, InventoryType.CHEST);
    }

    protected void setup() {
        this.canvas.setAutoSize(true);
        int width = this.canvas.getWidth();
        this.canvas.setMinSize(new Vector2d(width, 1));
        this.canvas.setMaxSize(new Vector2d(width, 6));
    }

    @Override
    public void render() {
        super.render();
        this.updateHeight();
    }

    private void updateHeight() {
        int newHeight = ChestCanvasGUI.limitHeight(this.canvas.getHeight(), this.canvas.getMaxSize().getY());
        if (this.lastUpdateRows != newHeight) {
            this.lastUpdateRows = newHeight;
            this.reloadViews();
        }
    }

    private static int limitHeight(int height, int maxHeight) {
        return Math.max(1, Math.min(maxHeight, height));
    }

}
