package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

import java.util.List;

/**
 * The ContentPane of a {@link PaginatorGUIPane}.
 * Internal use only.
 */
public class PaginatorContentPane extends SimpleItemGUIPane {
    /**
     * The according Paginator
     */
    private final PaginatorGUIPane paginatorGUIPane;

    /**
     * Creates a new PaginatorContentPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param paginatorGUIPane The according Paginator
     */
    protected PaginatorContentPane(GUI gui, Vector2d minSize, Vector2d maxSize, PaginatorGUIPane paginatorGUIPane) {
        super(gui, minSize, maxSize, true);
        this.paginatorGUIPane = paginatorGUIPane;
    }

    /**
     * Updates the size of the pane based on the size of the paginator.
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSize(Sizeable parent) {
        Vector2d newSize = this.paginatorGUIPane.getContentPaneSize();
        this.setMinSize(newSize);
        this.setSize(newSize);
    }

    /**
     * Is called when the size of the Pane changes.
     * Here it resets the Items in the Paginator.
     */
    @SuppressWarnings("unused")
    @Override
    public void onSizeChange() {
        this.setItems();
    }

    /**
     * Sets the Items in the Paginator
     */
    public void setItems() {
        this.clear();
        int startIndex = this.getSlots()*this.paginatorGUIPane.getCurrentPage();
        List<GUIItem> items = this.paginatorGUIPane.getItems();
        for (int i = 0; i < this.getSlots() && i < items.size() - startIndex; i++) {
            this.setSectionAt(i, items.get(i+startIndex));
        }
    }
}
