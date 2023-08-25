package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

/**
 * Builder for ChestGUIs
 */
@Getter
@SuppressWarnings("unused")
public class ChestGUIBuilder extends BaseChestGUIBuilder<SimpleGUIPane, ChestGUI, ChestGUIBuilder> {
    /**
     * Creates a new ChestGUIBuilder
     * @param plugin The plugin for the GUI
     */
    public ChestGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    /**
     * Builds the ChestGUI
     * @return The new ChestGUI
     */
    @Override
    public ChestGUI build() {
        return new ChestGUI(this.getPlugin(), this.getTitle(), this.getBestMinHeight(), this.getBestMaxHeight(), this.getRegisterDefaultHandlers());
    }
}
