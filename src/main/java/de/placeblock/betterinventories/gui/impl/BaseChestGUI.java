package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;


public abstract class BaseChestGUI<P extends GUIPane> extends BaseCanvasGUI<P> {
    private int lastUpdateRows = 1;

    public BaseChestGUI(Plugin plugin, TextComponent title) {
        super(plugin, title, InventoryType.CHEST);
    }

    protected void initialize() {
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
        int newHeight = BaseChestGUI.limitHeight(this.canvas.getHeight(), this.canvas.getMaxSize().getY());
        if (this.lastUpdateRows != newHeight) {
            this.lastUpdateRows = newHeight;
            this.reloadViews();
        }
    }

    private static int limitHeight(int height, int maxHeight) {
        return Math.max(1, Math.min(maxHeight, height));
    }

}
