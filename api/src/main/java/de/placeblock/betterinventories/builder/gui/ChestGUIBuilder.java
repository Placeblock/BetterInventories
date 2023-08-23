package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import lombok.Getter;
import org.bukkit.plugin.Plugin;


@Getter
@SuppressWarnings("unused")
public class ChestGUIBuilder extends BaseChestGUIBuilder<SimpleGUIPane, ChestGUI, ChestGUIBuilder> {
    public ChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public ChestGUI build() {
        return new ChestGUI(this.getPlugin(), this.getTitle(), this.getBestMinHeight(), this.getBestMaxHeight());
    }
}
