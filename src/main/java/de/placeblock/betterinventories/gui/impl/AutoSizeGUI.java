package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class AutoSizeGUI extends SingleCanvasGUI {
    private int lastUpdateRows = 1;

    public AutoSizeGUI(Plugin plugin, TextComponent title, int maxHeight) {
        super(plugin, title, InventoryType.CHEST);
        maxHeight = AutoSizeGUI.limitHeight(maxHeight, 6);
        this.canvas.setAutoSize(true);
        int width = this.canvas.getWidth();
        this.canvas.setMinSize(new Vector2d(width, 1));
        this.canvas.setMaxSize(new Vector2d(width, maxHeight));
    }

    @Override
    public void update() {
        this.render();
        this.updateHeight();
        this.updateViews();
    }

    private void updateHeight() {
        int newHeight = AutoSizeGUI.limitHeight(this.canvas.getHeight(), this.canvas.getMaxSize().getY());
        if (this.lastUpdateRows != newHeight) {
            this.lastUpdateRows = newHeight;
            this.reloadViews();
        }
    }

    private static int limitHeight(int height, int maxHeight) {
        return Math.max(1, Math.min(maxHeight, height));
    }
}
