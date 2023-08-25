package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.builder.content.PaginatorBuilder;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.HorizontalSplitGUIPane;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * A Paginator is a {@link GUIPane} that can contain items. If there are too many items you can switch pages to see all items.
 * <p></p>
 * Builder: {@link PaginatorBuilder}
 */
@SuppressWarnings("unused")
public class PaginatorGUIPane extends HorizontalSplitGUIPane implements ItemAddable<PaginatorGUIPane> {
    /**
     * The Items added to the Paginator
     */
    @Getter
    private final List<GUIItem> items = new ArrayList<>();

    /**
     * The default-controls or null
     */
    private final PaginatorControlsPane defaultControls;

    /**
     * The content-Pane where the Items are placed onto
     */
    private final PaginatorContentPane contentPane;

    /**
     * The current active Page
     */
    @Getter
    private int currentPage;

    /**
     * Whether to jump back to the first page when reaching the last page (and via versa)
     */
    @Getter
    @Setter
    private boolean repeat;

    /**
     * Creates a new PaginatorGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The minimum size of the Pane
     * @param repeat Whether to jump back to the first page when reaching the last page (and via versa)
     * @param startPage The start page
     * @param defaultControlsPosition The default controls automatically appear if there
     *                                is not enough space for all items. Set to null if you don't want
     *                                automatic controls, or you want to handle them yourself. To add custom controls
     *                                you can instantiate the {@link PaginatorControlsPane}
     */
    public PaginatorGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean repeat, int startPage, PaginatorControlsPosition defaultControlsPosition) {
        super(gui, minSize, maxSize);
        this.contentPane = new PaginatorContentPane(gui, minSize, maxSize, this);
        this.setUpperPane(this.contentPane);
        this.currentPage = startPage;
        this.repeat = repeat;
        if (defaultControlsPosition != null) {
            this.defaultControls = new PaginatorControlsPane(gui, this, new Vector2d(minSize.getX(), 1), new Vector2d(maxSize.getX(), 1), true, defaultControlsPosition);
        } else {
            this.defaultControls = null;
        }
    }

    /**
     * Updates the size of the Paginator based on how many Items are added.
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSize(Sizeable parent) {
        int newWidth = Math.max(this.items.size(),2);
        int newHeight = (int) Math.ceil(this.items.size() * 1F / newWidth);
        this.setSize(new Vector2d(newWidth, newHeight));
        this.currentPage = Math.min(this.currentPage, this.getPages());
        if (this.showDefaultControls()) {
            this.setLowerPane(this.defaultControls);
        } else {
            this.setLowerPane(null);
        }
    }

    /**
     * Is called to recursively update the size of all {@link GUIPane}s
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSizeRecursive(Sizeable parent) {
        this.updateSize(parent);
        this.contentPane.updateSizeRecursive(parent);
        if (this.defaultControls != null) {
            this.defaultControls.updateSizeRecursive(parent);
        }
    }

    /**
     * Sets all Items in the Paginator.
     * @param items The new Items
     * @return this
     * @param <I> The type of the items
     */
    public <I extends GUIItem> PaginatorGUIPane setItems(List<I> items) {
        this.items.clear();
        this.items.addAll(items);
        this.contentPane.setItems();
        return this;
    }

    /**
     * Is called whenever Items are added to the Paginator.
     * Resets Items in the content-pane
     */
    @Override
    public void onItemAdd() {
        this.contentPane.setItems();
    }

    /**
     * Clears all Items in this Paginator
     */
    public PaginatorGUIPane clearItems() {
        this.items.clear();
        this.contentPane.setItems();
        return this;
    }

    /**
     * Skips to the next page.
     */
    @SuppressWarnings("UnusedReturnValue")
    public PaginatorGUIPane nextPage() {
        this.setCurrentPage(this.getNextPage());
        return this;
    }

    /**
     * @return The next page index
     */
    private int getNextPage() {
        int pages = this.getPages();
        if (this.currentPage + 1 > pages && !this.repeat) return this.currentPage;
        return (this.currentPage + 1) % pages;
    }

    /**
     * Returns to the previous page.
     */
    @SuppressWarnings("UnusedReturnValue")
    public PaginatorGUIPane previousPage() {
        this.setCurrentPage(this.getPreviousPage());
        return this;
    }

    /**
     * @return The previous page index
     */
    private int getPreviousPage() {
        if (this.currentPage == 0 && !this.repeat) return 0;
        return Util.modulo(this.currentPage - 1, this.getPages());
    }

    /**
     * Sets the current page
     */
    @SuppressWarnings("UnusedReturnValue")
    public PaginatorGUIPane setCurrentPage(int index) {
        this.currentPage = index;
        this.contentPane.setItems();
        return this;
    }

    /**
     * @return the amount of pages
     */
    public int getPages() {
        return (int) Math.ceil(this.items.size()*1F/this.contentPane.getSlots());
    }

    /**
     * @return The size of the content-pane according to the size of the Paginator
     */
    public Vector2d getContentPaneSize() {
        int height = this.showDefaultControls() ? this.getHeight() - 1 : this.getHeight();
        return new Vector2d(this.getWidth(), height);
    }

    /**
     * @return True if default-controls should get displayed
     */
    private boolean showDefaultControls() {
        return this.showDefaultControls(this.items.size(), this.getSlots());
    }

    /**
     * @return True if default-controls should get displayed
     */
    private boolean showDefaultControls(int itemSize, int slots) {
        return itemSize > slots && this.defaultControls != null;
    }
}
