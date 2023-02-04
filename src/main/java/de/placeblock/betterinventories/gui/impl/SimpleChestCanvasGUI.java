package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.content.SimpleGUIPaneBuilder;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
public class SimpleChestCanvasGUI extends ChestCanvasGUI<SimpleGUIPane> {
    public SimpleChestCanvasGUI(Plugin plugin, TextComponent title, int height) {
        super(plugin, title);
        this.canvas = new SimpleGUIPaneBuilder(this).size(new Vector2d(9, height)).build();
        this.setup();
    }
}
