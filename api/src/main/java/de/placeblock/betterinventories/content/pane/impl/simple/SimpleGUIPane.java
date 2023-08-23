package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.builder.content.SimpleGUIPaneBuilder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * Implementation of {@link BaseSimpleGUIPane} that can contain all {@link GUISection}s.
 * <p></p>
 * Builder: {@link SimpleGUIPaneBuilder}
 */
@SuppressWarnings("unused")
public class SimpleGUIPane extends BaseSimpleGUIPane<GUISection, SimpleGUIPane> {
    public SimpleGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        this(gui, minSize, maxSize, false);
    }

    public SimpleGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
    }
}
