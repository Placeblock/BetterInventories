package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.CanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class CanvasGUIBuilder extends BaseCanvasGUIBuilder<SimpleGUIPane, CanvasGUI, CanvasGUIBuilder> {
    private Integer height;

    public CanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public CanvasGUI build() {
        if (this.height != null) {
            return new CanvasGUI(this.getPlugin(), this.getTitle(), this.getHeight());
        } else {
            return new CanvasGUI(this.getPlugin(), this.getTitle(), this.getType());
        }
    }
}
