package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class PaginatorGUIPaneBuilder extends BaseGUIPaneBuilder<PaginatorGUIPane, PaginatorGUIPaneBuilder> {
    private boolean repeat = true;
    private int startPage = 0;
    private final List<GUIItem> items = new ArrayList<>();

    public PaginatorGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    public PaginatorGUIPaneBuilder repeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public PaginatorGUIPaneBuilder item(GUIItem item) {
        this.items.add(item);
        return this;
    }

    public PaginatorGUIPaneBuilder startPage(int index) {
        this.startPage = index;
        return this;
    }

    public PaginatorGUIPaneBuilder items(Collection<GUIItem> items) {
        this.items.addAll(items);
        return this;
    }

    public PaginatorGUIPaneBuilder items(GUIItem... items) {
        this.items.addAll(new ArrayList<>(List.of(items)));
        return this;
    }

    protected boolean getRepeat() {
        return this.repeat;
    }

    @Override
    public PaginatorGUIPane build() {
        Vector2d maxSize = this.getMaxSize() == null ? this.getSize() : this.getMaxSize();
        Vector2d minSize = this.getMinSize() == null ? this.getSize() : this.getMinSize();
        PaginatorGUIPane paginatorGUIPane = new PaginatorGUIPane(this.getGui(),
                this.getSize(),
                maxSize,
                minSize,
                this.getAutoSize(),
                this.getRepeat(),
                this.startPage);
        for (GUIItem item : this.items) {
            paginatorGUIPane.addItem(item);
        }
        return paginatorGUIPane;
    }
}
