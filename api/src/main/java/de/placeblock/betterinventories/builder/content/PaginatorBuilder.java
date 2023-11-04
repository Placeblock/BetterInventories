package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.paginator.ItemAddable;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorControlsPane;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorControlsPosition;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Builder for creating PaginatorBuilders
 */
@Deprecated(forRemoval = true)
@SuppressWarnings("unused")
public class PaginatorBuilder extends BaseGUIPaneBuilder<PaginatorGUIPane, PaginatorBuilder> implements ItemAddable<PaginatorBuilder> {
    /**
     * Whether to jump back to the first page when reaching the last page (and via versa)
     */
    private boolean repeat = true;

    /**
     * The start Page
     */
    private int startPage = 0;

    /**
     * The Items added to the Paginator
     */
    @Getter
    private final List<GUIItem> items = new ArrayList<>();

    /**
     * The default controls automatically appear if there
     * is not enough space for all items. Set to null if you don't want
     * automatic controls, or you want to handle them yourself.
     */
    private PaginatorControlsPosition defaultControlsPosition = null;

    /**
     * The default controls automatically appear if there
     * is not enough space for all items. Set to null if you don't want
     * automatic controls, or you want to handle them yourself.
     */
    private Function<PaginatorGUIPane, PaginatorControlsPane> defaultControls = null;

    /**
     * Creates a new PaginatorBuilder
     * @param gui The GUI for the Paginator
     */
    public PaginatorBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Sets whether to jump back to the first page when reaching the last page (and via versa)
     * @param repeat Do repeat
     * @return this
     */
    public PaginatorBuilder repeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    /**
     * Sets the start page
     * @param index The index of the start page
     * @return this
     */
    public PaginatorBuilder startPage(int index) {
        this.startPage = index;
        return this;
    }

    /**
     * Sets the default-controls
     * @param position The position of the default-controls
     * @return this
     */
    public PaginatorBuilder defaultControls(PaginatorControlsPosition position) {
        this.defaultControlsPosition = position;
        return this;
    }

    /**
     * Sets the default-controls
     * @param pane Function returning the default-controls
     * @return this
     */
    public PaginatorBuilder defaultControls(Function<PaginatorGUIPane, PaginatorControlsPane> pane) {
        this.defaultControls = pane;
        return this;
    }

    /**
     * @return Whether repeat is enabled
     */
    protected boolean getRepeat() {
        return this.repeat;
    }

    /**
     * Builds the Paginator
     * @return The new Paginator
     */
    @Override
    public PaginatorGUIPane build() {
        PaginatorGUIPane paginatorGUIPane;
        if (this.defaultControlsPosition != null) {
            paginatorGUIPane = new PaginatorGUIPane(this.getGui(),
                    this.getBestMinSize(), this.getBestMaxSize(),
                    this.getRepeat(),
                    this.defaultControlsPosition,
                    this.startPage);
        } else {
            paginatorGUIPane = new PaginatorGUIPane(this.getGui(),
                    this.getBestMinSize(), this.getBestMaxSize(),
                    this.getRepeat(),
                    this.startPage,
                    this.defaultControls);
        }
        paginatorGUIPane.addItems(this.items);
        return paginatorGUIPane;
    }
}
