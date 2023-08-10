package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@SuppressWarnings("unused")
public class PaginatorGUIPane extends SimpleGUIPane implements ItemAddable<PaginatorGUIPane> {
    @Getter
    private final List<GUIItem> items = new ArrayList<>();
    private final PaginatorControlsPane defaultControls;
    private final PaginatorContentPane contentPane;

    @Getter
    private int currentPage;

    @Getter
    @Setter
    private boolean repeat;

    public PaginatorGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean repeat, int currentPage, PaginatorControlsPosition defaultControlsPosition) {
        super(gui, minSize, maxSize);
        this.contentPane = new PaginatorContentPane(gui, minSize, maxSize, this);
        this.setSectionAt(new Vector2d(), this.contentPane);
        this.currentPage = currentPage;
        this.repeat = repeat;
        if (defaultControlsPosition != null) {
            this.defaultControls = new PaginatorControlsPane(gui, this, new Vector2d(minSize.getX(), 1), new Vector2d(maxSize.getX(), 1), true, defaultControlsPosition);
            this.setDefaultControls();
        } else {
            this.defaultControls = null;
        }
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        int newWidth = Math.max(Math.min(parentMaxSize.getX(), this.items.size()),2);
        int itemHeight = (int) Math.ceil(this.items.size() * 1F / newWidth);
        int realHeight = Math.min(parentMaxSize.getY(), itemHeight);
        this.setSize(new Vector2d(newWidth, realHeight));

        this.currentPage = Math.min(this.currentPage, this.getPages());
        this.setDefaultControls();
    }

    @Override
    public void updateSizeRecursive(Vector2d parentMaxSize) {
        this.updateSize(parentMaxSize);
        this.updateChildrenRecursive(parentMaxSize);
    }

    private void setDefaultControls() {
        this.removeSection(this.defaultControls);
        if (this.showDefaultControls()) {
            this.setSectionAt(new Vector2d(0, this.getContentPaneSize().getY()), this.defaultControls);
        }
    }

    public <I extends GUIItem> PaginatorGUIPane setItems(List<I> items) {
        this.items.clear();
        this.items.addAll(items);
        this.contentPane.setItems();
        return this;
    }

    @Override
    public void onItemAdd() {
        this.contentPane.setItems();
    }

    public PaginatorGUIPane clearItems() {
        this.items.clear();
        this.contentPane.setItems();
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public PaginatorGUIPane nextPage() {
        this.setCurrentPage(this.getNextPage());
        return this;
    }

    private int getNextPage() {
        return (this.currentPage + 1) % this.getPages();
    }

    @SuppressWarnings("UnusedReturnValue")
    public PaginatorGUIPane previousPage() {
        this.setCurrentPage(this.getPreviousPage());
        return this;
    }

    private int getPreviousPage() {
        return Util.modulo(this.currentPage - 1, this.getPages());
    }

    @SuppressWarnings("UnusedReturnValue")
    public PaginatorGUIPane setCurrentPage(int index) {
        this.currentPage = index;
        this.contentPane.setItems();
        return this;
    }

    public int getPages() {
        return (int) Math.ceil(this.items.size()*1F/this.contentPane.getSlots());
    }

    public Vector2d getContentPaneSize() {
        int height = this.showDefaultControls() ? this.getHeight() - 1 : this.getHeight();
        return new Vector2d(this.getWidth(), height);
    }

    private boolean showDefaultControls() {
        return this.showDefaultControls(this.items.size(), this.getSlots());
    }

    private boolean showDefaultControls(int itemSize, int slots) {
        return itemSize > slots && this.defaultControls != null;
    }
}
