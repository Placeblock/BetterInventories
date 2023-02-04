package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.impl.CanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseSingleCanvasGUIBuilder <P extends GUIPane, G extends CanvasGUI<P>, B extends BaseSingleCanvasGUIBuilder<P, G, B>> extends BaseGUIBuilder<G, B> {
    private Integer height;

    public BaseSingleCanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    public B height(int height) {
        this.height = height;
        return (B) this;
    }

    protected Integer getHeight() {
        if (this.height == null && this.getType() == null) {
            throw new IllegalStateException("Height is null in builder");
        }
        return this.height;
    }
}
