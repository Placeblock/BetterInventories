package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorControlsPosition;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class PaginatorGUIPaneBuilder extends BaseGUIPaneBuilder<PaginatorPane, PaginatorGUIPaneBuilder> {
    private boolean repeat = true;
    private int startPage = 0;
    private final List<GUIItem> items = new ArrayList<>();
    private boolean defaultControls = true;
    private PaginatorControlsPosition defaultControlsPosition = PaginatorControlsPosition.RIGHT;

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

    public PaginatorGUIPaneBuilder hideControls() {
        this.defaultControls = false;
        return this;
    }

    public PaginatorGUIPaneBuilder items(Collection<GUIItem> items) {
        this.items.addAll(items);
        return this;
    }

    public PaginatorGUIPaneBuilder items(GUIItem... items) {
        return this.items(new ArrayList<>(List.of(items)));
    }

    public <T> PaginatorGUIPaneBuilder items(Function<T, GUIItem> converter, Collection<T> items) {
        for (T item : items) {
            this.item(converter.apply(item));
        }
        return this;
    }

    public <T> PaginatorGUIPaneBuilder items(Function<T, GUIItem> converter, T... items) {
        return this.items(converter, new ArrayList<>(List.of(items)));
    }

    public PaginatorGUIPaneBuilder defaultControlsPosition(PaginatorControlsPosition position) {
        this.defaultControlsPosition = position;
        return this;
    }

    protected boolean getRepeat() {
        return this.repeat;
    }

    @Override
    public PaginatorPane build() {
        Vector2d maxSize = this.getMaxSize() == null ? this.getSize() : this.getMaxSize();
        Vector2d minSize = this.getMinSize() == null ? this.getSize() : this.getMinSize();
        PaginatorPane paginatorPane = new PaginatorPane(this.getGui(),
                this.getSize(),
                maxSize,
                minSize,
                this.getAutoSize(),
                this.getRepeat(),
                this.startPage,
                this.defaultControls,
                this.defaultControlsPosition);
        for (GUIItem item : this.items) {
            paginatorPane.addItem(item);
        }
        return paginatorPane;
    }
}
