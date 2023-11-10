package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * Implementation of {@link BaseSimpleGUIPane} that can contain only {@link GUIItem}.
 */
@SuppressWarnings("unused")
public class SimpleItemGUIPane extends BaseSimpleItemGUIPane<SimpleItemGUIPane> {

    /**
     * Creates a new SimpleGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    @Deprecated(forRemoval = true)
    public SimpleItemGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
    }

    /**
     * Builder for creating {@link SimpleItemGUIPane}
     */
    public static class Builder extends BaseSimpleItemGUIPane.Builder<Builder, SimpleItemGUIPane> {
        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public SimpleItemGUIPane build() {
            return new SimpleItemGUIPane(this.getGui(), this.getMinSize(), this.getMaxSize(), this.isAutoSize());
        }
        @Override
        protected Builder self() {
            return this;
        }
    }
}
