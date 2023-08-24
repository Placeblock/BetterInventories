package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.gui.ChestGUIBuilder;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

/**
 * Implementation of {@link BaseChestGUI} using SimpleGUIPane that auto resizes
 * <p></p>
 * Builder: {@link ChestGUIBuilder}
 */
@Getter
public class ChestGUI extends BaseChestGUI<SimpleGUIPane> {
    @Setter
    private int maxHeight;
    @Setter
    private int minHeight;

    @SuppressWarnings("unused")
    public ChestGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, height, height);
    }

    @SuppressWarnings("unused")
    public ChestGUI(Plugin plugin, TextComponent title, int height, boolean registerDefaultHandlers) {
        this(plugin, title, height, height, registerDefaultHandlers);
    }

    public ChestGUI(Plugin plugin, TextComponent title, int minHeight, int maxHeight) {
        this(plugin, title, minHeight, maxHeight, true);
    }

    public ChestGUI(Plugin plugin, TextComponent title, int minHeight, int maxHeight, boolean registerDefaultHandlers) {
        super(plugin, title, minHeight, maxHeight, registerDefaultHandlers);
        this.setCanvas(new SimpleGUIPane(this, this.getMinSize(), this.getMaxSize(), true));
    }
}
