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
    /**
     * Creates a new SimpleGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     */
    public SimpleGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        this(gui, minSize, maxSize, false);
    }

    /**
     * Creates a new SimpleGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public SimpleGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
    }
}
