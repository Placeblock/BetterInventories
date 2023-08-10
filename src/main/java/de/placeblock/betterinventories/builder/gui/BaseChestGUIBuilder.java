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
    @SuppressWarnings("unchecked")
    public B maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return (B) this;
    }
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
