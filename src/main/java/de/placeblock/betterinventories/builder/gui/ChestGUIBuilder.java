package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


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
        super.build();
        return new ChestGUI(this.getPlugin(), this.getTitle(), this.maxHeight);
    }
}
