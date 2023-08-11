package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.impl.BaseCanvasGUI;
import org.bukkit.plugin.Plugin;


@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseCanvasGUIBuilder<P extends GUIPane, G extends BaseCanvasGUI<P>, B extends BaseCanvasGUIBuilder<P, G, B>> extends BaseGUIBuilder<G, B> {
    protected Integer height;

    public BaseCanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    /**
     * Sets the height of the inventory
     */
    public B height(int height) {
        this.height = height;
        return (B) this;
    }

    protected Integer getHeight() {
        return this.height;
    }
}
