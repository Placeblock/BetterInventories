package de.placeblock.betterinventories.content.pane.impl.vanilla;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * Can be used to synchronize an IOGUIPane with another SimpleItemGUIPane
 */
@SuppressWarnings("unused")
public class SynchedGUIPane extends IOGUIPane {
    /**
     * The pane to keep in sync
     */
    private final SimpleItemGUIPane targetPane;

    /**
     * Creates a new SynchedGUIPane
     * @param gui The GUI
     * @param size The size
     * @param autoSize Whether to autoSize
     * @param targetPane The pane to keep in sync
     */
    public SynchedGUIPane(GUI gui, Vector2d size, boolean autoSize, SimpleItemGUIPane targetPane) {
        this(gui, size, size, autoSize, targetPane);
    }

    /**
     * Creates a new SynchedGUIPane
     * @param gui The GUI
     * @param minSize The minSize
     * @param maxSize The maxSize
     * @param targetPane The pane to keep in sync
     */
    public SynchedGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, SimpleItemGUIPane targetPane) {
        this(gui, minSize, maxSize, true, targetPane);
    }

    /**
     * Creates a new SynchedGUIPane
     * @param gui The GUI
     * @param minSize The minSize
     * @param maxSize The maxSize
     * @param targetPane The pane to keep in sync
     * @param autoSize Whether to autoSize
     */
    public SynchedGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize, SimpleItemGUIPane targetPane) {
        super(gui, minSize, maxSize, autoSize);
        this.targetPane = targetPane;
    }

    @Override
    public void onItemChange(Vector2d position) {
        this.targetPane.setSectionAt(position, this.getItem(position));
    }
}
