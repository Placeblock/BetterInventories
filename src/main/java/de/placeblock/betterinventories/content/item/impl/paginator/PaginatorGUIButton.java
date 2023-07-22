package de.placeblock.betterinventories.content.item.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatorGUIButton extends GUIButton {

    protected final PaginatorGUIPane paginatorGUIPane;

    public PaginatorGUIButton(PaginatorGUIPane paginatorGUIPane, GUI gui, ItemStack item) {
        super(gui, item);
        this.paginatorGUIPane = paginatorGUIPane;
    }
}
