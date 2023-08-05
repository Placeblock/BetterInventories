package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.gui.impl.ChestGUI;
import org.bukkit.plugin.Plugin;


@SuppressWarnings("unused")
public class ChestGUIBuilder extends CanvasGUIBuilder {
    private int maxHeight;

    public ChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }
    public ChestGUIBuilder maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    @Override
    public ChestGUI build() {
        return new ChestGUI(this.getPlugin(), this.getTitle(), this.maxHeight);
    }
}
