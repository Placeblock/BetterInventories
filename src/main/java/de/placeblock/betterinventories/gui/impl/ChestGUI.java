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
    public ChestGUI(Plugin plugin, TextComponent title, int size) {
        this(plugin, title, size, size);
    }

    public ChestGUI(Plugin plugin, TextComponent title, int minHeight, int maxHeight) {
        super(plugin, title, minHeight, maxHeight);
        this.setCanvas(new SimpleGUIPane(this, this.getMinSize(), this.getMaxSize(), true));
    }
}
