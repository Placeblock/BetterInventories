package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import org.bukkit.plugin.Plugin;


public class ChestGUIBuilder extends BaseChestGUIBuilder<SimpleGUIPane, ChestGUI, ChestGUIBuilder> {
    public ChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    @Override
    public ChestGUI build() {
        super.build();
        return new ChestGUI(this.getPlugin(), this.getTitle(), this.getHeight());
    }
}
