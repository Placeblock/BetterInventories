package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.SimpleChestCanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
public class SimpleChestCanvasGUIBuilder extends BaseSingleCanvasGUIBuilder<SimpleGUIPane, SimpleChestCanvasGUI, SimpleChestCanvasGUIBuilder> {
    public SimpleChestCanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public SimpleChestCanvasGUI build() {
        if (this.getHeight() == null) {
            throw new IllegalStateException("Height is null in builder");
        }
        return new SimpleChestCanvasGUI(this.getPlugin(), this.getTitle(), this.getHeight());
    }
}
