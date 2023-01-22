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
    private final List<GUIItem> items;
    private final boolean repeat;
    private final SimpleGUIPane controlsPane;
    private final SimpleGUIPane contentPane;
    private final GUIButton nextButton;
    private final GUIButton prevButton;

    public PaginatorGUIPane(GUI gui, int width, int height) {
        this(gui, width, height, true);
    }

    public PaginatorGUIPane(GUI gui, int width, int height, boolean repeat) {
        this(gui, width, height, repeat, new ArrayList<>());
    }

    public PaginatorGUIPane(GUI gui, int width, int height, boolean repeat, List<GUIItem> pages) {
        this(gui, width, height, repeat, 0, pages);
    }

    public PaginatorGUIPane(GUI gui, int width, int height, boolean repeat, int currentPage, List<GUIItem> items) {
        super(gui, width, height);
        if (width < 2) {
            throw new IllegalArgumentException("The width of a PaginatorGUIPane has a minimum width of 2");
        }
        this.repeat = repeat;
        this.controlsPane = new SimpleGUIPane(gui, width, 1);
        this.controlsPane.fill(FILL_ITEM);
        this.contentPane = new SimpleGUIPane(gui, width, height-1);
        this.items = items;

        this.nextButton = new GUIButton(gui, Util.getArrowItem(ArrowDirection.RIGHT)) {
            @Override
            public void onClick(Player player, boolean shift) {
                PaginatorGUIPane.this.nextPage();
            }
        };
        this.prevButton = new GUIButton(gui, Util.getArrowItem(ArrowDirection.LEFT)) {
            @Override
            public void onClick(Player player, boolean shift) {
                PaginatorGUIPane.this.previousPage();
            }
        };
        this.setPage(currentPage);
    }

    public void addItem(GUIItem pane) {
        this.items.add(pane);
        this.update();
    }

    private void update() {
        this.updateContent();
        this.updateArrows();
    }

    private void updateArrows() {
        this.controlsPane.setSectionAt(this.getWidth()-2, (this.currentPage > 0 || this.repeat) ?
                this.prevButton : FILL_ITEM);
        this.controlsPane.setSectionAt(this.getWidth()-1, (this.currentPage < this.getPages() - 1) || this.repeat ?
                this.nextButton : FILL_ITEM);
    }

    private void updateContent() {
        this.contentPane.clear();
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
        List<ItemStack> content = this.getEmptyContentArray(ItemStack.class);
        if (this.currentPage < this.getPages()) {
            content = this.renderOnList(new Vector2d(), this.contentPane, content);
            content = this.renderOnList(new Vector2d(0, this.getHeight()-1), this.controlsPane, content);
        }
        return content;
    }

    @Override
    public GUISection getSectionAt(Vector2d position) {
        System.out.println("GETTING SECTION AT ("+this+") " + position);
        if (position.getY() < this.getHeight()-1) {
            return this.contentPane.getSectionAt(position);
        } else {
            return this.controlsPane.getSectionAt(position.subtract(new Vector2d(0, this.getHeight()-1)));
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
        this.update();
        this.getGui().update();
    }
}
