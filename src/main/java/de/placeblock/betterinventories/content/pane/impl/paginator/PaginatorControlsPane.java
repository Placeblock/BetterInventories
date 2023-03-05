package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ArrowDirection;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.Sound;


public class PaginatorControlsPane extends SimpleGUIPane {
    public static final GUIItem FILL_ITEM = new GUIItem(null, GUI.PLACEHOLDER_ITEM);
    private final PaginatorGUIPane paginatorGUIPane;
    private int[] buttonIndices;
    private GUIButton nextButton;
    private GUIButton previousButton;
    private final PaginatorControlsPosition position;

    public PaginatorControlsPane(GUI gui, PaginatorGUIPane paginatorGUIPane, Vector2d size, PaginatorControlsPosition position) {
        super(gui, size);
        this.paginatorGUIPane = paginatorGUIPane;
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
        int currentPage = this.paginatorGUIPane.getCurrentPage();
        int pages = this.paginatorGUIPane.getPages();
        boolean repeat = this.paginatorGUIPane.isRepeat();
        this.setSectionAt(this.buttonIndices[0], (currentPage > 0 || repeat) ?
                this.previousButton : FILL_ITEM);
        this.setSectionAt(this.buttonIndices[1], (currentPage < pages - 1) || repeat ?
                this.nextButton : FILL_ITEM);
    }

    private GUIButton getNextButton(GUI gui) {
        return new GUIButton(gui, Util.getArrowItem(ArrowDirection.RIGHT), Sound.ITEM_BOOK_PAGE_TURN) {
            @Override
            public void onClick(ClickData data) {
                PaginatorControlsPane.this.paginatorGUIPane.nextPage();
                PaginatorControlsPane.this.getGui().update();
            }
        };
    }

    private GUIButton getPreviousButton(GUI gui) {
        return new GUIButton(gui, Util.getArrowItem(ArrowDirection.LEFT), Sound.ITEM_BOOK_PAGE_TURN) {
            @Override
            public void onClick(ClickData data) {
                PaginatorControlsPane.this.paginatorGUIPane.previousPage();
                PaginatorControlsPane.this.getGui().update();
            }
        };
    }
}
