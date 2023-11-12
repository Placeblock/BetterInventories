package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.impl.paginator.NextPageGUIButton;
import de.placeblock.betterinventories.content.item.impl.paginator.PreviousPageGUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * The Controls pane for a {@link PaginatorGUIPane}.
 */
public class PaginatorControlsPane extends BasePaginatorControlsPane<PaginatorControlsPane> {
    /**
     * Creates a new {@link PaginatorControlsPane}
     *
     * @param gui              The GUI
     * @param paginatorGUIPane The according Paginator
     * @param minSize          The minimum size of the Pane
     * @param maxSize          The maximum size of the Pane
     * @param autoSize         Whether the Pane should synchronize its width with the Paginator.
     * @param position         The position where the Buttons should get rendered
     * @param nextButton       The custom next-button
     * @param previousButton   The custom previous-button
     */
    protected PaginatorControlsPane(GUI gui, PaginatorGUIPane paginatorGUIPane, Vector2d minSize, Vector2d maxSize, boolean autoSize, PaginatorControlsPosition position, NextPageGUIButton nextButton, PreviousPageGUIButton previousButton) {
        super(gui, paginatorGUIPane, minSize, maxSize, autoSize, position, nextButton, previousButton);
    }

    /**
     * Builder for {@link PaginatorControlsPane}
     */
    public static class Builder extends AbstractBuilder<Builder, PaginatorControlsPane> {

        /**
         * Creates a new Builder
         *
         * @param gui       The GUI this Pane belongs to
         * @param paginator The paginator this Pane belongs to
         */
        protected Builder(GUI gui, PaginatorGUIPane paginator) {
            super(gui, paginator);
        }

        @Override
        public PaginatorControlsPane build() {
            return new PaginatorControlsPane(this.getGui(), this.getPaginator(), this.getMinSize(),
                    this.getMaxSize(), this.isAutoSize(), this.getPosition(), this.getNextButton(), this.getPreviousButton());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
