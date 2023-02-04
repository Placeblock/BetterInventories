package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.content.pane.impl.FramedGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

/**
 * Author: Placeblock
 */
@Getter
public class FramedGUI extends ChestCanvasGUI<FramedGUIPane> {

    public FramedGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, height, null);
    }

    public FramedGUI(Plugin plugin, TextComponent title, int height, GUI backInventory) {
        this(plugin, title, height, backInventory, false);
    }

    public FramedGUI(Plugin plugin, TextComponent title, int height, GUI backInventory, boolean vertical) {
        super(plugin, title);
        if (!vertical && height < 3) {
            throw new IllegalArgumentException("Expected minimum size of 3 but got " + height + " in FramedGUI");
        }
        this.canvas = new FramedGUIPane(this, new Vector2d(9, height), vertical, backInventory);
        this.setup();
    }

    public SimpleGUIPane getFrame() {
        return this.canvas.getFrame();
    }

    @Override
    public void render() {
        super.render();
    }
}
