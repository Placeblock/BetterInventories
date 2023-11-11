package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.item.impl.paginator.NextPageGUIButton;
import de.placeblock.betterinventories.content.item.impl.paginator.PreviousPageGUIButton;
import de.placeblock.betterinventories.content.pane.impl.simple.BaseSimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

/**
 * The Controls pane for a {@link PaginatorGUIPane}.
 */
public class PaginatorControlsPane extends BaseSimpleItemGUIPane<PaginatorControlsPane> {
    /**
     * The default fill-item for the Pane
     */
    public static final GUIItem FILL_ITEM = new GUIItem.Builder(null)
            .itemStack(new ItemBuilder(Component.empty(), Material.BLACK_STAINED_GLASS_PANE).build())
            .build();

    /**
     * The according Paginator
     */
    private final PaginatorGUIPane paginatorGUIPane;

    /**
     * The Button used for skipping to the next page
     */
    private final NextPageGUIButton nextButton;

    /**
     * The Button used for returning to the previous page
     */
    private final PreviousPageGUIButton previousButton;

    /**
     * The position where the Buttons should get rendered
     */
    private final PaginatorControlsPosition position;

    /**
     * Whether the Pane should synchronize its width with the Paginator
     */
    @Setter
    private boolean autoSize;

    /**
     * Creates a new PaginatorControlsPane
     * @param gui The GUI
     * @param paginatorGUIPane The according Paginator
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether the Pane should synchronize its width with the Paginator.
     * @param position The position where the Buttons should get rendered
     * @param nextButton The custom next-button
     * @param previousButton The custom previous-button
     */
    protected PaginatorControlsPane(GUI gui,
                                 PaginatorGUIPane paginatorGUIPane,
                                 Vector2d minSize,
                                 Vector2d maxSize,
                                 boolean autoSize,
                                 PaginatorControlsPosition position,
                                 NextPageGUIButton nextButton,
                                 PreviousPageGUIButton previousButton) {
        super(gui, minSize, maxSize, false);
        this.paginatorGUIPane = paginatorGUIPane;
        this.position = position;
        this.autoSize = autoSize;
        this.nextButton = nextButton;
        this.previousButton = previousButton;
        this.onSizeChange();
    }

    /**
     * Resets the Buttons
     */
    private void updateButtons() {
        this.removeSection(this.nextButton);
        this.removeSection(this.previousButton);
        int[] buttonIndices = this.position.calculateIndices.apply(this.getSize().getX());
        int currentPage = this.paginatorGUIPane.getCurrentPage();
        int pages = this.paginatorGUIPane.getPages();
        boolean repeat = this.paginatorGUIPane.isRepeat();
        this.setSectionAt(buttonIndices[0], (currentPage > 0 || repeat) ?
                this.previousButton : FILL_ITEM);
        this.setSectionAt(buttonIndices[1], ((currentPage < (pages - 1)) || repeat) ?
                this.nextButton : FILL_ITEM);
    }

    /**
     * Updates its size if {@link PaginatorControlsPane#autoSize} is enabled
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSize(Sizeable parent) {
        if (this.autoSize) {
            this.setSize(new Vector2d(this.paginatorGUIPane.getWidth(), 1));
        }
    }

    /**
     * Is called when the size of the Pane changes.
     * Here it resets the Items in the Paginator.
     */
    @Override
    public void onSizeChange() {
        this.clear();
        this.fill(FILL_ITEM);
        this.updateButtons();
    }

    /**
     * Builder for {@link PaginatorControlsPane}
     */
    @SuppressWarnings("unused")
    public static class Builder extends AbstractBuilder<Builder, PaginatorControlsPane> {
        private final PaginatorGUIPane paginator;
        private PaginatorControlsPosition position = PaginatorControlsPosition.RIGHT;
        private NextPageGUIButton nextButton;
        private PreviousPageGUIButton previousButton;

        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         * @param paginator The paginator this Pane belongs to
         */
        public Builder(GUI gui, PaginatorGUIPane paginator) {
            super(gui);
            this.paginator = paginator;
            this.nextButton = new NextPageGUIButton.Builder(gui, paginator)
                    .itemStack(new ItemBuilder(Component.text("Next Page"), Material.ARROW).build())
                    .build();
            this.previousButton = new PreviousPageGUIButton.Builder(gui, paginator)
                    .itemStack(new ItemBuilder(Component.text("Previous Page"), Material.ARROW).build())
                    .build();
        }

        /**
         * Sets the position attribute
         * @param position The position of the buttons inside the pane
         * @return Itself
         */
        public Builder position(PaginatorControlsPosition position) {
            this.position = position;
            return this;
        }

        /**
         * Sets the nextButton attribute
         * @param nextButton The button used to get to the next page
         * @return Itself
         */
        public Builder nextButton(NextPageGUIButton nextButton) {
            this.nextButton = nextButton;
            return this;
        }

        /**
         * Sets the previousButton attribute
         * @param previousButton The button used to return to the previous page
         * @return Itself
         */
        public Builder previousButton(PreviousPageGUIButton previousButton) {
            this.previousButton = previousButton;
            return this;
        }

        @Override
        public PaginatorControlsPane build() {
            return new PaginatorControlsPane(this.getGui(), this.paginator, this.getMinSize(),
                    this.getMaxSize(), this.isAutoSize(), this.position, this.nextButton, this.previousButton);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
