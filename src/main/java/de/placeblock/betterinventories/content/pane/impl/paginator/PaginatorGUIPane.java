package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@SuppressWarnings("unused")
public class PaginatorGUIPane extends SimpleGUIPane {
    @Getter
    private final List<GUIItem> items = new ArrayList<>();
    private final PaginatorControlsPane defaultControls;
    private final PaginatorContentPane contentPane;

    @Getter
    private int currentPage;

    @Getter
    @Setter
    private boolean repeat;

    public PaginatorGUIPane(GUI gui, Vector2d maxSize, boolean repeat, int currentPage, PaginatorControlsPosition defaultControlsPosition) {
        super(gui, maxSize, maxSize);
        this.contentPane = new PaginatorContentPane(gui, maxSize, this);
        this.setSectionAt(new Vector2d(), this.contentPane);
        this.currentPage = currentPage;
        this.repeat = repeat;
        if (defaultControlsPosition != null) {
            this.defaultControls = new PaginatorControlsPane(gui, this, new Vector2d(maxSize.getX(), 1), defaultControlsPosition);
            this.setDefaultControls();
        } else {
            this.defaultControls = null;
        }
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        int newWidth = Math.max(Math.min(this.maxSize.getX(), this.items.size()),2);
        int itemHeight = (int) Math.ceil(this.items.size() * 1F / newWidth);
        int actualHeight = Math.min(this.maxSize.getY(), itemHeight);
        this.setSize(new Vector2d(newWidth, actualHeight));

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

    @Override
    public PaginatorGUIPane addItem(GUIItem item) {
        this.items.add(item);
        this.contentPane.setItems();
        return this;
    }

    public void clearItems() {
        this.items.clear();
        this.contentPane.setItems();
    }

    public void nextPage() {
        this.setCurrentPage(this.getNextPage());
    }

    private int getNextPage() {
        return (this.currentPage + 1) % this.getPages();
    }

    public void previousPage() {
        this.setCurrentPage(this.getPreviousPage());
    }

    private int getPreviousPage() {
        return Util.modulo(this.currentPage - 1, this.getPages());
    }

    public void setCurrentPage(int index) {
        this.currentPage = index;
        this.contentPane.setItems();
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
