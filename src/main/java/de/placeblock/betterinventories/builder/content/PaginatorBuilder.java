package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.paginator.ItemAddable;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorControlsPosition;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class PaginatorBuilder extends BaseGUIPaneBuilder<PaginatorGUIPane, PaginatorBuilder> implements ItemAddable<PaginatorBuilder> {
    private boolean repeat = true;
    private int startPage = 0;
    @Getter
    private final List<GUIItem> items = new ArrayList<>();
    private PaginatorControlsPosition defaultControlsPosition = null;

    public PaginatorBuilder(GUI gui) {
        super(gui);
    }

    public PaginatorBuilder repeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public PaginatorBuilder startPage(int index) {
        this.startPage = index;
        return this;
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
                this.getBestMinSize(), this.getBestMaxSize(),
                this.getRepeat(),
                this.startPage,
                this.defaultControlsPosition);
        paginatorGUIPane.addItems(this.items);
        return paginatorGUIPane;
    }
}
