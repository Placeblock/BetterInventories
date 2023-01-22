package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class PaginatorGUIPaneBuilder extends BaseGUIPaneBuilder<PaginatorGUIPane, PaginatorGUIPaneBuilder> {
    private boolean repeat = true;
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

    public PaginatorGUIPaneBuilder items(List<GUIItem> items) {
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
        return new PaginatorGUIPane(this.getGui(), this.getWidth(), this.getHeight(), this.getRepeat(), this.items);
    }
}
