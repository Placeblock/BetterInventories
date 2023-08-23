package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.builder.content.SimpleItemGUIPaneBuilder;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * Implementation of {@link BaseSimpleGUIPane} that can contain only {@link GUIItem}s.
 * <p></p>
 * Builder: {@link SimpleItemGUIPaneBuilder}
 */
public class SimpleItemGUIPane extends BaseSimpleGUIPane<GUIItem, SimpleItemGUIPane> {
    public SimpleItemGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        this(gui, minSize, maxSize, false);
    }
    public SimpleItemGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
    }
}
