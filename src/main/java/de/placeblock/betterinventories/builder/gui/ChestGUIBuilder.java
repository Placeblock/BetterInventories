package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.List;


@Getter
@SuppressWarnings("unused")
public class ChestGUIBuilder extends BaseCanvasGUIBuilder<SimpleGUIPane, ChestGUI, ChestGUIBuilder> {
    private Integer maxHeight;
    private Integer minHeight;

    public ChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }
    public ChestGUIBuilder maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }
    public ChestGUIBuilder minHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public int getBestMaxHeight() {
        return this.getValue(List.of(this::getMaxHeight, this::getMinHeight, this::getHeight));
    }

    public int getBestMinHeight() {
        return this.getValue(List.of(this::getMinHeight, this::getHeight, () -> 1));
    }

    @Override
    public ChestGUI build() {
        return new ChestGUI(this.getPlugin(), this.getTitle(), this.getBestMinHeight(), this.getBestMaxHeight());
    }
}
