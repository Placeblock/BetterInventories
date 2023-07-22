package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorControlsPosition;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


@SuppressWarnings("unused")
public class PaginatorBuilder extends BaseGUIPaneBuilder<PaginatorGUIPane, PaginatorBuilder> {
    private boolean repeat = true;
    private int startPage = 0;
    private final List<GUIItem> items = new ArrayList<>();
    private PaginatorControlsPosition defaultControlsPosition = PaginatorControlsPosition.RIGHT;

    public PaginatorBuilder(GUI gui) {
        super(gui);
    }

    public PaginatorBuilder repeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public PaginatorBuilder item(GUIItem item) {
        this.items.add(item);
        return this;
    }

    public PaginatorBuilder startPage(int index) {
        this.startPage = index;
        return this;
    }

    public PaginatorBuilder items(Collection<GUIItem> items) {
        this.items.addAll(items);
        return this;
    }

    public PaginatorBuilder items(GUIItem... items) {
        return this.items(List.of(items));
    }

    public <T> PaginatorBuilder items(Function<T, GUIItem> converter, Collection<T> items) {
        for (T item : items) {
            this.item(converter.apply(item));
        }
        return this;
    }

    public <T> PaginatorBuilder items(Function<T, GUIItem> converter, T[] items) {
        return this.items(converter, new ArrayList<>(List.of(items)));
    }

    public PaginatorBuilder defaultControls(PaginatorControlsPosition position) {
        this.defaultControlsPosition = position;
        return this;
    }

    protected boolean getRepeat() {
        return this.repeat;
    }

    @Override
    public PaginatorGUIPane build() {
        PaginatorGUIPane paginatorGUIPane = new PaginatorGUIPane(this.getGui(),
                this.getMaxSize(),
                this.getRepeat(),
                this.startPage,
                this.defaultControlsPosition);
        for (GUIItem item : this.items) {
            paginatorGUIPane.addItem(item);
        }
        return paginatorGUIPane;
    }
}
