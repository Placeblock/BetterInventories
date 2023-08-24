package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.item.impl.paginator.NextPageGUIButton;
import de.placeblock.betterinventories.content.item.impl.paginator.PreviousPageGUIButton;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

/**
 * The Controls pane for a {@link PaginatorGUIPane}.
 */
public class PaginatorControlsPane extends SimpleGUIPane {
    public static final GUIItem FILL_ITEM = new GUIItem(null, new ItemBuilder(Component.empty(), Material.BLACK_STAINED_GLASS_PANE).build());
    private final PaginatorGUIPane paginatorGUIPane;
    private final NextPageGUIButton nextButton;
    private final PreviousPageGUIButton previousButton;
    private final PaginatorControlsPosition position;
    @Setter
    private boolean autoSize;

    /**
     * @param autoSize Whether to set the width of the ControlsPane automatically to the width of the Paginator.
     * @param position The position of the Controls
     */
    public PaginatorControlsPane(GUI gui,
                                 PaginatorGUIPane paginatorGUIPane,
                                 Vector2d minSize,
                                 Vector2d maxSize,
                                 boolean autoSize,
                                 PaginatorControlsPosition position) {
        this(gui, paginatorGUIPane, minSize,
                maxSize, autoSize, position, new NextPageGUIButton(paginatorGUIPane, gui),
                new PreviousPageGUIButton(paginatorGUIPane, gui));
    }

    /**
     * @param autoSize Whether to set the width of the ControlsPane automatically to the width of the Paginator.
     * @param position The position of the Controls
     * @param nextButton The custom next button
     * @param previousButton The custom previous button
     */
    public PaginatorControlsPane(GUI gui,
                                 PaginatorGUIPane paginatorGUIPane,
                                 Vector2d minSize,
                                 Vector2d maxSize,
                                 boolean autoSize,
                                 PaginatorControlsPosition position,
                                 NextPageGUIButton nextButton,
                                 PreviousPageGUIButton previousButton) {
        super(gui, minSize, maxSize);
        this.paginatorGUIPane = paginatorGUIPane;
        this.position = position;
        this.autoSize = autoSize;
        this.nextButton = nextButton;
        this.previousButton = previousButton;
        this.onSizeChange();
    }

    private void updateButtons() {
        this.removeSection(this.nextButton);
        this.removeSection(this.previousButton);
        int[] buttonIndices = this.position.calculateIndices.apply(this.getSize().getX());
        int currentPage = this.paginatorGUIPane.getCurrentPage();
        int pages = this.paginatorGUIPane.getPages();
        boolean repeat = this.paginatorGUIPane.isRepeat();
        this.setSectionAt(buttonIndices[0], (currentPage > 0 || repeat) ?
                this.previousButton : FILL_ITEM);
        this.setSectionAt(buttonIndices[1], ((currentPage < (pages - 1)) || repeat) ?
                this.nextButton : FILL_ITEM);
    }

    @Override
    public void updateSize(GUIPane parent) {
        if (this.autoSize) {
            this.setSize(new Vector2d(this.paginatorGUIPane.getWidth(), 1));
        }
    }

    @Override
    public void onSizeChange() {
        this.clear();
        this.fill(FILL_ITEM);
        this.updateButtons();
    }
}
