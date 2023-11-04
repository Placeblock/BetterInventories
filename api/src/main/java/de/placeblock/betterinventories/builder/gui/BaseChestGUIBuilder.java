package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.impl.BaseChestGUI;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Base class for creating ChestGUI-Builders
 * @param <P> The main canvas type
 * @param <G> The GUI type
 * @param <B> The Builder type
 */
@Deprecated(forRemoval = true)
@Getter
@SuppressWarnings("unused")
public abstract class BaseChestGUIBuilder<P extends GUIPane, G extends BaseChestGUI<P>, B extends BaseChestGUIBuilder<P, G, B>> extends BaseCanvasGUIBuilder<P, G, B> {
    /**
     * The maximum height of the GUI
     */
    private Integer maxHeight;

    /**
     * The minimum height of the GUI
     */
    private Integer minHeight;

    /**
     * Creates a new BaseChestGUIBuilder
     * @param plugin The plugin for the GUI
     */
    public BaseChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    /**
     * Sets the maxHeight of the Inventory.
     * If no maxHeight is set at build-time it will use minHeight or Height
     * @param maxHeight The maximum height
     * @return this
     */
    @SuppressWarnings("unchecked")
    public B maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return (B) this;
    }

    /**
     * Sets the minHeight of the Inventory.
     * If no minHeight is set at build-time it will use Height or 1
     * @param minHeight The minimum height
     * @return this
     */
    @SuppressWarnings("unchecked")
    public B minHeight(int minHeight) {
        this.minHeight = minHeight;
        return (B) this;
    }

    /**
     * @return The best maximum height (maxHeight -> minHeight -> height)
     */
    public int getBestMaxHeight() {
        return this.getValue(List.of(this::getMaxHeight, this::getMinHeight, this::getHeight));
    }

    /**
     * @return The best minimum height (minHeight -> height -> 1)
     */
    public int getBestMinHeight() {
        return this.getValue(List.of(this::getMinHeight, this::getHeight, () -> 1));
    }

}
