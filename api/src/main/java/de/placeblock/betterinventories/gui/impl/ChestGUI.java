package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.gui.ChestGUIBuilder;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

/**
 * Implementation of {@link BaseChestGUI} using SimpleGUIPane that auto resizes
 * <br>
 * Builder: {@link ChestGUIBuilder}
 */
public class ChestGUI extends BaseChestGUI<SimpleGUIPane> {
    /**
     * Creates a new ChestGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param height The height of the GUI
     */
    @SuppressWarnings("unused")
    public ChestGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, height, height);
    }

    /**
     * Creates a new ChestGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param minHeight The minimum height of the GUI
     * @param maxHeight The maximum height of the GUI
     */
    public ChestGUI(Plugin plugin, TextComponent title, int minHeight, int maxHeight) {
        super(plugin, title, minHeight, maxHeight);
        this.setCanvas(new SimpleGUIPane(this, this.getMinSize(), this.getMaxSize(), true));
    }
}
