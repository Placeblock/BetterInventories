package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.item.impl.paginator.NextPageGUIButton;
import de.placeblock.betterinventories.content.item.impl.paginator.PreviousPageGUIButton;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;


public class PaginatorControlsPane extends SimpleGUIPane {
    public static final GUIItem FILL_ITEM = new GUIItem(null, new ItemBuilder(Component.empty(), Material.BLACK_STAINED_GLASS_PANE).build());
    private final PaginatorGUIPane paginatorGUIPane;
    private NextPageGUIButton nextButton;
    private PreviousPageGUIButton previousButton;
    private final PaginatorControlsPosition position;

    public PaginatorControlsPane(GUI gui, PaginatorGUIPane paginatorGUIPane, Vector2d maxSize, PaginatorControlsPosition position) {
        super(gui, maxSize, maxSize);
        this.paginatorGUIPane = paginatorGUIPane;
        this.position = position;
        this.init();
    }

    public void init() {
        this.clear();
        this.fill(FILL_ITEM);
        this.nextButton = new NextPageGUIButton(this.paginatorGUIPane, this.getGui());
        this.previousButton = new PreviousPageGUIButton(this.paginatorGUIPane, this.getGui());
        this.updateButtons();
    }

    public void updateButtons() {
        this.removeSection(this.nextButton);
        this.removeSection(this.previousButton);
        int[] buttonIndices = this.position.calculateIndices.apply(size.getX());
        int currentPage = this.paginatorGUIPane.getCurrentPage();
        int pages = this.paginatorGUIPane.getPages();
        boolean repeat = this.paginatorGUIPane.isRepeat();
        this.setSectionAt(buttonIndices[0], (currentPage > 0 || repeat) ?
                this.previousButton : FILL_ITEM);
        this.setSectionAt(buttonIndices[1], (currentPage < pages - 1) || repeat ?
                this.nextButton : FILL_ITEM);
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        this.setSize(new Vector2d(this.paginatorGUIPane.getWidth(), 1));
    }

    @Override
    public void onSizeChange() {
        this.clear();
        this.fill(FILL_ITEM);
        this.updateButtons();
    }
}
