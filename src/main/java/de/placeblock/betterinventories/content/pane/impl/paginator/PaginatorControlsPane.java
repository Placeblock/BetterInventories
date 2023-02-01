package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ArrowDirection;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.entity.Player;

/**
 * Author: Placeblock
 */
public class PaginatorControlsPane extends SimpleGUIPane {
    public static final GUIItem FILL_ITEM = new GUIItem(null, GUI.PLACEHOLDER_ITEM);
    private final PaginatorPane paginatorPane;
    private int[] buttonIndices;
    private GUIButton nextButton;
    private GUIButton previousButton;
    private PaginatorControlsPosition position;

    public PaginatorControlsPane(GUI gui, PaginatorPane paginatorPane, Vector2d size, PaginatorControlsPosition position) {
        super(gui, size);
        this.paginatorPane = paginatorPane;
        this.position = position;
        this.init();
    }

    public void init() {
        this.clear();
        this.fill(FILL_ITEM);
        this.buttonIndices = this.position.calculateIndices.apply(size.getX());
        this.nextButton = this.getNextButton(this.getGui());
        this.previousButton = this.getPreviousButton(this.getGui());
        this.updateButtons();
    }

    public void updateButtons() {
        int currentPage = this.paginatorPane.getCurrentPage();
        int pages = this.paginatorPane.getPages();
        boolean repeat = this.paginatorPane.isRepeat();
        this.setSectionAt(this.buttonIndices[0], (currentPage > 0 || repeat) ?
                this.previousButton : FILL_ITEM);
        this.setSectionAt(this.buttonIndices[1], (currentPage < pages - 1) || repeat ?
                this.nextButton : FILL_ITEM);
    }

    private GUIButton getNextButton(GUI gui) {
        return new GUIButton(gui, Util.getArrowItem(ArrowDirection.RIGHT)) {
            @Override
            public void onClick(Player player, int slot) {
                PaginatorControlsPane.this.paginatorPane.nextPage();
                PaginatorControlsPane.this.getGui().update();
            }
        };
    }

    private GUIButton getPreviousButton(GUI gui) {
        return new GUIButton(gui, Util.getArrowItem(ArrowDirection.LEFT)) {
            @Override
            public void onClick(Player player, int slot) {
                PaginatorControlsPane.this.paginatorPane.previousPage();
                PaginatorControlsPane.this.getGui().update();
            }
        };
    }
}
