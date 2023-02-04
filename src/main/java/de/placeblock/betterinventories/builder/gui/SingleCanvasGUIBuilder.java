package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.SimpleCanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SingleCanvasGUIBuilder extends BaseSingleCanvasGUIBuilder<SimpleGUIPane, SimpleCanvasGUI, SingleCanvasGUIBuilder>{
    private Integer height;

    public SingleCanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public SimpleCanvasGUI build() {
        if (this.height != null) {
            return new SimpleCanvasGUI(this.getPlugin(), this.getTitle(), this.getHeight());
        } else {
            return new SimpleCanvasGUI(this.getPlugin(), this.getTitle(), this.getType());
        }
    }
}
