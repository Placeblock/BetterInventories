package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.content.pane.impl.FramedGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;


@Getter
public class FramedGUI extends BaseChestGUI<FramedGUIPane> {
    public static final int FRAMED_GUI_MAX_WIDTH = 9;
    public static final int FRAMED_GUI_MAX_HEIGHT = 6;
    public static final Vector2d FRAMED_GUI_VERTICAL_MIN = new Vector2d(3, 1);
    public static final Vector2d FRAMED_GUI_HORIZONTAL_MIN = new Vector2d(1, 3);
    public static final int FRAMED_GUI_FRAME_HORIZONTAL_MAX_HEIGHT = FRAMED_GUI_MAX_HEIGHT - 2;
    public static final int FRAMED_GUI_FRAME_VERTICAL_MAX_HEIGHT = FRAMED_GUI_MAX_HEIGHT;

    public FramedGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, height, null);
    }

    public FramedGUI(Plugin plugin, TextComponent title, int height, Supplier<GUI> backGUI) {
        this(plugin, title, height, backGUI, false);
    }

    public FramedGUI(Plugin plugin, TextComponent title, int height, Supplier<GUI> backGUI, boolean vertical) {
        super(plugin, title);
        this.canvas = new FramedGUIPane(this, new Vector2d(FRAMED_GUI_MAX_WIDTH, height), vertical, backGUI);
        this.initialize();
    }

    public SimpleGUIPane getFrame() {
        return this.canvas.getFrame();
    }
}
