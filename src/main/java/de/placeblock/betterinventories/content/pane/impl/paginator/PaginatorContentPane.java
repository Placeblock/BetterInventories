package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

import java.util.List;

public class PaginatorContentPane extends SimpleGUIPane {
    private final PaginatorGUIPane paginatorGUIPane;

    public PaginatorContentPane(GUI gui, Vector2d maxSize, PaginatorGUIPane paginatorGUIPane) {
        super(gui, maxSize, maxSize);
        this.paginatorGUIPane = paginatorGUIPane;
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
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
