package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.impl.BaseChestGUI;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Getter
@SuppressWarnings("unused")
public abstract class BaseChestGUIBuilder<P extends GUIPane, G extends BaseChestGUI<P>, B extends BaseChestGUIBuilder<P, G, B>> extends BaseCanvasGUIBuilder<P, G, B> {
    private Integer maxHeight;
    private Integer minHeight;

    public BaseChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }


    /**
     * Sets the maxHeight of the Inventory.
     * If no maxHeight is set at build-time it will use minHeight or Height
     */
    @SuppressWarnings("unchecked")
    public B maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return (B) this;
    }

    /**
     * Sets the minHeight of the Inventory.
     * If no minHeight is set at build-time it will use Height or 1
     */
    @SuppressWarnings("unchecked")
    public B minHeight(int minHeight) {
        this.minHeight = minHeight;
        return (B) this;
    }

    public int getBestMaxHeight() {
        return this.getValue(List.of(this::getMaxHeight, this::getMinHeight, this::getHeight));
    }

    public int getBestMinHeight() {
        return this.getValue(List.of(this::getMinHeight, this::getHeight, () -> 1));
    }

}
