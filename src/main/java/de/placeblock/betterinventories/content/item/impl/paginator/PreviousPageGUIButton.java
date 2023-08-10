package de.placeblock.betterinventories.content.item.impl.paginator;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;

/**
 * A {@link GUIButton} for the {@link PaginatorGUIPane} which returns to the previous page.
 */
public class PreviousPageGUIButton extends PaginatorGUIButton {
    public PreviousPageGUIButton(PaginatorGUIPane paginatorGUIPane, GUI gui, TextComponent title, Material material) {
        super(paginatorGUIPane, gui, new ItemBuilder(title, material).build());
    }
    public PreviousPageGUIButton(PaginatorGUIPane paginatorGUIPane, GUI gui, TextComponent title) {
        this(paginatorGUIPane, gui, title, Material.ARROW);
    }
    public PreviousPageGUIButton(PaginatorGUIPane paginatorGUIPane, GUI gui) {
        this(paginatorGUIPane, gui, Component.text("Vorherige Seite").decoration(TextDecoration.ITALIC, false));
    }

    @Override
    public void onClick(ClickData data) {
        this.paginatorGUIPane.previousPage();
        this.getGui().update();
    }
}
