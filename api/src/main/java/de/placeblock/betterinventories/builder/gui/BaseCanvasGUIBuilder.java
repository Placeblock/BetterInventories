package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.impl.BaseCanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Base class for creating CanvasGUI-Builders
 * @param <P> The main canvas type
 * @param <G> The GUI type
 * @param <B> The Builder type
 */
@Deprecated(forRemoval = true)
@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseCanvasGUIBuilder<P extends GUIPane, G extends BaseCanvasGUI<P>, B extends BaseCanvasGUIBuilder<P, G, B>> extends BaseGUIBuilder<G, B> {
    /**
     * The height of the GUI
     */
    protected Integer height;

    /**
     * Creates a new BaseCanvasGUIBuilder
     * @param plugin The plugin for the GUI
     */
    public BaseCanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    /**
     * Sets the height of the inventory
     * @param height The height
     * @return this
     */
    public B height(int height) {
        this.height = height;
        return (B) this;
    }

    /**
     * @return The height of the GUI
     */
    protected Integer getHeight() {
        return this.height;
    }
}
