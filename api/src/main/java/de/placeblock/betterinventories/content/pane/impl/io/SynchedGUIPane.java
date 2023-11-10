package de.placeblock.betterinventories.content.pane.impl.io;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

/**
 * Can be used to synchronize an IOGUIPane with another SimpleItemGUIPane
 */
@SuppressWarnings("unused")
public class SynchedGUIPane extends BaseIOGUIPane<SynchedGUIPane> {
    /**
     * The pane to keep in sync
     */
    private final SimpleItemGUIPane targetPane;

    /**
     * Creates a new SynchedGUIPane
     * @param gui The GUI
     * @param minSize The minSize
     * @param maxSize The maxSize
     * @param targetPane The pane to keep in sync
     * @param autoSize Whether to autoSize
     * @param input Whether it should be allowed to input items into the IO-Pane.
     * @param output Whether it should be allowed to remove items from the IO-Pane.
     */
    @Deprecated(forRemoval = true)
    public SynchedGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize,
                          boolean input, boolean output, SimpleItemGUIPane targetPane) {
        super(gui, minSize, maxSize, autoSize, input, output);
        this.targetPane = targetPane;
    }

    @Override
    public void onItemChange(Vector2d position, ItemStack itemStack) {
        this.targetPane.setSectionAt(position, this.getItem(position));
    }

    /**
     * Builder for creating {@link SynchedGUIPane}
     */
    public static class Builder extends BaseIOGUIPane.Builder<Builder, SynchedGUIPane> {

        private SimpleItemGUIPane targetPane;

        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        /**
         * Sets the targetPane attribute
         * @param targetPane The target pane to sync the items to
         * @return Itself
         */
        public Builder targetPane(SimpleItemGUIPane targetPane) {
            this.targetPane = targetPane;
            return this;
        }

        @Override
        public SynchedGUIPane build() {
            if (this.targetPane == null) {
                throw new IllegalStateException("targetPane cannot be null");
            }
            return new SynchedGUIPane(this.getGui(), this.getMinSize(), this.getMaxSize(),
                    this.isAutoSize(), this.isInput(), this.isOutput(), this.targetPane);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
