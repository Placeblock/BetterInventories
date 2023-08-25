package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.CanvasGUI;
import org.bukkit.plugin.Plugin;

/**
 * Builder for creating CanvasGUIs
 */
@SuppressWarnings("unused")
public class CanvasGUIBuilder extends BaseCanvasGUIBuilder<SimpleGUIPane, CanvasGUI, CanvasGUIBuilder> {

    /**
     * Creates a new CanvasGUIBuilder
     * @param plugin The plugin for the GUI
     */
    public CanvasGUIBuilder(Plugin plugin) {
        super(plugin);
    }

    /**
     * Builds the CanvasGUI
     * @return The new CanvasGUI
     */
    @Override
    public CanvasGUI build() {
        if (this.height != null) {
            return new CanvasGUI(this.getPlugin(), this.getTitle(), this.getHeight(), this.getRegisterDefaultHandlers());
        } else {
            return new CanvasGUI(this.getPlugin(), this.getTitle(), this.getType(), this.getRegisterDefaultHandlers());
        }
    }
}
