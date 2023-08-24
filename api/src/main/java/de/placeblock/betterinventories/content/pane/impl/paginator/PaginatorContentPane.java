package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

import java.util.List;

/**
 * The ContentPane of a {@link PaginatorGUIPane}.
 * Internal use only.
 */
public class PaginatorContentPane extends SimpleItemGUIPane {
    private final PaginatorGUIPane paginatorGUIPane;

    public PaginatorContentPane(GUI gui, Vector2d minSize, Vector2d maxSize, PaginatorGUIPane paginatorGUIPane) {
        super(gui, minSize, maxSize);
        this.paginatorGUIPane = paginatorGUIPane;
    }

    @Override
    public void updateSize(GUIPane parent) {
        this.setSize(this.paginatorGUIPane.getContentPaneSize());
    }

    @Override
    public void onSizeChange() {
        this.setItems();
    }

    public void setItems() {
        this.clear();
        int startIndex = this.getSlots()*this.paginatorGUIPane.getCurrentPage();
        List<GUIItem> items = this.paginatorGUIPane.getItems();
        for (int i = 0; i < this.getSlots() && i < items.size() - startIndex; i++) {
            this.setSectionAt(i, items.get(i+startIndex));
        }
    }
}
