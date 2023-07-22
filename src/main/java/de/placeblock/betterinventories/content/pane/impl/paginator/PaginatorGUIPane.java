package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@SuppressWarnings("unused")
public class PaginatorGUIPane extends SimpleGUIPane {
    private final List<GUIItem> items = new ArrayList<>();
    private final PaginatorControlsPane defaultControls;
    private final PaginatorContentPane contentPane;
    private int currentPage;
    @Setter
    private boolean repeat;

    public PaginatorGUIPane(GUI gui, Vector2d maxSize, boolean repeat, int currentPage, PaginatorControlsPosition defaultControlsPosition) {
        super(gui, maxSize);
        if (this.getWidth() < 2) {
            throw new IllegalArgumentException("The width of a PaginatorGUIPane has a minimum of 2");
        }
        this.contentPane = new PaginatorContentPane(gui, maxSize, this);
        this.setSectionAt(0, this.contentPane);
        this.currentPage = currentPage;
        this.repeat = repeat;
        if (defaultControlsPosition != null) {
            this.defaultControls = new PaginatorControlsPane(gui, this, new Vector2d(this.getWidth(), 1), defaultControlsPosition);
            this.setDefaultControls();
        } else {
            this.defaultControls = null;
        }
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        int newWidth = Math.min(this.maxSize.getX(), this.items.size());
        int newHeight = (int) Math.min(Math.ceil(this.items.size() * 1F / newWidth), this.maxSize.getY());
        this.setSize(new Vector2d(newWidth, newHeight));
    }

    @Override
    public void onSizeChange() {
        this.currentPage = Math.min(this.currentPage, this.getPages());
        this.setDefaultControls();
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
        this.setCurrentPage((this.currentPage + 1) % this.getPages());
    }

    public void previousPage() {
        this.setCurrentPage(Util.modulo(this.currentPage - 1, this.getPages()));
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
        return this.items.size() > this.getSlots() && this.defaultControls != null;
    }
}
