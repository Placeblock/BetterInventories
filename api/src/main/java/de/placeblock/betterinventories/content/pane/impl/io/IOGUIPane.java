package de.placeblock.betterinventories.content.pane.impl.io;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * GUIPane which allows Items to be inserted and taken out
 */
@SuppressWarnings("unused")
public class IOGUIPane extends BaseIOGUIPane<IOGUIPane> {
    /**
     * Creates a new TransferGUIPane
     *
     * @param gui      The GUI
     * @param minSize  The minimum size of the Pane
     * @param maxSize  The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     * @param input    Whether it should be allowed to input items into the IO-Pane.
     * @param output   Whether it should be allowed to remove items from the IO-Pane.
     * @param onItemChange Executed when an item in the pane changes
     */
    protected IOGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize, boolean input, boolean output, IOConsumer onItemChange) {
        super(gui, minSize, maxSize, autoSize, input, output, onItemChange);
    }

    /**
     * Builder for creating {@link IOGUIPane}
     */
    @SuppressWarnings("unused")
    public static class Builder extends BaseIOGUIPane.Builder<Builder, IOGUIPane> {
        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public IOGUIPane build() {
            return new IOGUIPane(this.getGui(), this.getMinSize(), this.getMaxSize(),
                    this.isAutoSize(), this.isInput(), this.isOutput(), this.getOnChange());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
