package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.impl.BaseChestGUI;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;


public abstract class BaseChestGUIBuilder<P extends GUIPane, G extends BaseChestGUI<P>, B extends BaseChestGUIBuilder<P, G, B>> extends BaseCanvasGUIBuilder<P, G, B> {
    public BaseChestGUIBuilder(Plugin plugin) {
        super(plugin);
        this.type(InventoryType.CHEST);
    }

    @Override
    public G build() {
        if (this.getHeight() == null) {
            throw new IllegalStateException("Height is null in builder");
        }
        return null;
    }
}
