package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.SimpleChestCanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
public class ChestGUIBuilder extends BaseChestGUIBuilder<SimpleGUIPane, SimpleChestCanvasGUI, ChestGUIBuilder> {
    public ChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public SimpleChestCanvasGUI build() {
        super.build();
        return new SimpleChestCanvasGUI(this.getPlugin(), this.getTitle(), this.getHeight());
    }
}
