package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.builder.content.PaginatorBuilder;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;

/**
 * Author: Placeblock
 */
@Getter
public class PaginatedFramedGUI extends FramedGUI {

    private final PaginatorGUIPane paginator;


    public PaginatedFramedGUI(Plugin plugin, TextComponent title, int height) {
        this(plugin, title, height, null);
    }

    public PaginatedFramedGUI(Plugin plugin, TextComponent title, Supplier<GUI> backInventory) {
        this(plugin, title, 1, FramedGUI.FRAMED_GUI_MAX_HEIGHT, backInventory, false, true);
    }

    public PaginatedFramedGUI(Plugin plugin, TextComponent title, int height, boolean autoSize) {
        this(plugin, title, height, null, autoSize);
    }

    public PaginatedFramedGUI(Plugin plugin, TextComponent title, int maxHeight, Supplier<GUI> backInventory) {
        this(plugin, title, 1, maxHeight, backInventory, false, true);
    }

    public PaginatedFramedGUI(Plugin plugin, TextComponent title, int height, int maxHeight, boolean autoSize) {
        this(plugin, title, height, maxHeight, null, autoSize);
    }

    public PaginatedFramedGUI(Plugin plugin, TextComponent title, int height, Supplier<GUI> backInventory, boolean autoSize) {
        this(plugin, title, height, FramedGUI.FRAMED_GUI_MAX_HEIGHT, backInventory, false, autoSize);
    }

    public PaginatedFramedGUI(Plugin plugin, TextComponent title, int height, int maxHeight, Supplier<GUI> backInventory, boolean autoSize) {
        this(plugin, title, height, maxHeight, backInventory, false, autoSize);
    }

    public PaginatedFramedGUI(Plugin plugin, TextComponent title, int height, int maxHeight, Supplier<GUI> backInventory, boolean vertical, boolean autoSize) {
        super(plugin, title, height, backInventory, vertical);
        int frameWidth = this.getFrame().getWidth();
        int frameHeight = this.getFrame().getHeight();
        int maxFrameHeight = this.getFrame().getMaxSize().getY();
        PaginatorBuilder paginatorBuilder = new PaginatorBuilder(this)
                .maxSize(new Vector2d(frameWidth, Math.min(vertical ? maxHeight : maxHeight - 2, maxFrameHeight)))
                .repeat(true);
        if (autoSize) {
            paginatorBuilder.autoSize();
            paginatorBuilder.size(new Vector2d(frameWidth, 1));
        } else {
            paginatorBuilder.size(new Vector2d(frameWidth, frameHeight));
        }
        this.paginator = paginatorBuilder.build();
        this.getFrame().addSection(paginator);
    }
}
