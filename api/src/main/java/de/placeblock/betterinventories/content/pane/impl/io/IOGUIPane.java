package de.placeblock.betterinventories.content.pane.impl.io;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

/**
 * GUIPane which allows Items to be inserted and taken out
 */
public abstract class IOGUIPane extends BaseIOGUIPane<IOGUIPane> {
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
     */
    @Deprecated(forRemoval = true)
    public IOGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize, boolean input, boolean output) {
        super(gui, minSize, maxSize, autoSize, input, output);
    }

    /**
     * Builder for creating {@link IOGUIPane}
     */
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
            return new IOGUIPane(this.getGui(), this.getMinSize(), this.getMaxSize(), this.isAutoSize(), this.isInput(), this.isOutput()) {
                @Override
                public void onItemChange(Vector2d position, ItemStack itemStack) {
                    BiConsumer<Vector2d, ItemStack> onChange = Builder.this.getOnChange();
                    if (onChange != null) {
                        onChange.accept(position, itemStack);
                    }
                }
            };
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
