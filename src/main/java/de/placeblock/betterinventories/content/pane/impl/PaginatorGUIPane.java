package de.placeblock.betterinventories.content.pane.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ArrowDirection;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Placeblock
 */
@Getter
@SuppressWarnings("unused")
public class PaginatorGUIPane extends GUIPane {
    public static final GUIItem FILL_ITEM = new GUIItem(null, new ItemBuilder(Component.empty(), Material.BLACK_STAINED_GLASS_PANE).build());
    private int currentPage;
    private final List<GUIItem> items = new ArrayList<>();
    private final boolean repeat;
    private final SimpleGUIPane controlsPane;
    private final SimpleGUIPane contentPane;
    private final GUIButton nextButton;
    private final GUIButton prevButton;
    private boolean showControls;

    public PaginatorGUIPane(GUI gui, Vector2d size, boolean repeat) {
        this(gui, size, size, size, false, repeat, 0);
    }

    public PaginatorGUIPane(GUI gui, Vector2d size, Vector2d maxSize, Vector2d minSize, boolean autoSize, boolean repeat, int currentPage) {
        super(gui, size, maxSize, minSize, autoSize);
        if (this.getWidth() < 2) {
            throw new IllegalArgumentException("The width of a PaginatorGUIPane has a minimum width of 2");
        }
        this.repeat = repeat;
        Vector2d controlsSize = new Vector2d(this.getWidth(), 1);
        this.controlsPane = new SimpleGUIPane(gui, controlsSize, controlsSize, controlsSize, false);
        this.controlsPane.fill(FILL_ITEM);
        Vector2d contentSize = new Vector2d(this.getWidth(), 1);
        this.contentPane = new SimpleGUIPane(gui, contentSize, new Vector2d(this.getWidth(), this.getMaxSize().getY()), contentSize, true);

        this.nextButton = new GUIButton(gui, Util.getArrowItem(ArrowDirection.RIGHT)) {
            @Override
            public void onClick(Player player) {
                PaginatorGUIPane.this.nextPage();
                PaginatorGUIPane.this.getGui().update();
            }
        };
        this.prevButton = new GUIButton(gui, Util.getArrowItem(ArrowDirection.LEFT)) {
            @Override
            public void onClick(Player player) {
                PaginatorGUIPane.this.previousPage();
                PaginatorGUIPane.this.getGui().update();
            }
        };
        this.setPage(currentPage);
    }

    public void addItem(GUIItem item) {
        this.items.add(item);
    }

    private void updateArrows() {
        this.controlsPane.setSectionAt(this.getWidth()-2, (this.currentPage > 0 || this.repeat) ?
                this.prevButton : FILL_ITEM);
        this.controlsPane.setSectionAt(this.getWidth()-1, (this.currentPage < this.getPages() - 1) || this.repeat ?
                this.nextButton : FILL_ITEM);
    }

    private void updateContent() {
        this.contentPane.clear();
        this.contentPane.setHeight(this.showControls ? this.getHeight() - 1 : this.getHeight());
        int startIndex = this.contentPane.getSlots()*this.currentPage;
        for (int i = 0; i < this.contentPane.getSlots() && i < (this.items.size() - startIndex); i++) {
            this.contentPane.setSectionAt(i, this.items.get(i+startIndex));
        }
    }

    private int getPages() {
        return (int) Math.ceil(this.items.size()*1F/this.contentPane.getSlots());
    }

    @Override
    public List<ItemStack> render() {
        this.checkUpdateSize();
        this.showControls = this.items.size() > this.getSlots();
        this.updateContent();
        this.updateArrows();
        List<ItemStack> rendered = this.getEmptyContentArray(ItemStack.class);
        rendered = this.renderOnList(new Vector2d(), this.contentPane, rendered);
        if (this.showControls) {
            rendered = this.renderOnList(new Vector2d(0, this.getHeight()-1), this.controlsPane, rendered);
        }
        return rendered;
    }

    @Override
    public GUISection getSectionAt(Vector2d position) {
        if (position.getY() >= this.getHeight()-1 && this.showControls) {
            return this.controlsPane.getSectionAt(position.subtract(new Vector2d(0, this.getHeight()-1)));
        } else {
            return this.contentPane.getSectionAt(position);
        }
    }

    public void nextPage() {
        this.setPage((this.currentPage + 1) % this.getPages());
    }

    public void previousPage() {
        this.setPage(Util.modulo(this.currentPage - 1, this.getPages()));
    }

    public void setPage(int index) {
        this.currentPage = index;
    }


    @Override
    protected void updateSize() {
        int newWidth = Math.min(this.getMaxSize().getX(), this.items.size());
        int newHeight = (int) Math.ceil(this.items.size()*1F/newWidth);
        this.setWidth(newWidth);
        this.setHeight(newHeight);
    }
}
