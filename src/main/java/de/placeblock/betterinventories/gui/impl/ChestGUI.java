package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.content.GUIPaneBuilder;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
public class ChestGUI extends BaseChestGUI<SimpleGUIPane> {
    public ChestGUI(Plugin plugin, TextComponent title, int height) {
        super(plugin, title);
        this.canvas = new GUIPaneBuilder(this).size(new Vector2d(9, height)).build();
        this.initialize();
    }
}
