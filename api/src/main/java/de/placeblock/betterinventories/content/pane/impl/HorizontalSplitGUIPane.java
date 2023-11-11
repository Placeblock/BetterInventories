package de.placeblock.betterinventories.content.pane.impl;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * A {@link GUIPane} with an upper and lower pane.
 * Renders the lower pane always below the upper pane.
 */
@SuppressWarnings("unused")
public class HorizontalSplitGUIPane extends BaseHorizontalSplitGUIPane{
    /**
     * Creates a new HorizontalSplitGUIPane
     *
     * @param gui       The GUI
     * @param minSize   The minimum size of the Pane
     * @param maxSize   The maximum size of the Pane
     * @param upperPane The upper pane
     * @param lowerPane The lower pane
     */
    protected HorizontalSplitGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, GUIPane upperPane, GUIPane lowerPane) {
        super(gui, minSize, maxSize, upperPane, lowerPane);
    }

    /**
     * Builder for creating {@link HorizontalSplitGUIPane}
     */
    @SuppressWarnings("unused")
    public static class Builder extends AbstractBuilder<Builder, HorizontalSplitGUIPane> {
        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public HorizontalSplitGUIPane build() {
            return new HorizontalSplitGUIPane(this.getGui(), this.getMinSize(), this.getMaxSize(),
                    this.getUpperPane(), this.getLowerPane());
        }

        @Override
        protected HorizontalSplitGUIPane.Builder self() {
            return this;
        }
    }

}
