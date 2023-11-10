package de.placeblock.betterinventories.content.item.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link GUIButton} for the {@link PaginatorGUIPane} which returns to the previous page.
 */
public class PreviousPageGUIButton extends PaginatorGUIButton {
    /**
     * Creates a new PreviousPageGUIButton
     * @param paginatorGUIPane The according Paginator
     * @param gui The GUI
     */
    public PreviousPageGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, PaginatorGUIPane paginatorGUIPane) {
        super(gui, item, cooldown, sound, permission, paginatorGUIPane);
    }

    @Override
    public void onClick(ClickData data) {
        this.paginatorGUIPane.previousPage();
        this.getGui().update();
    }

    public static class Builder extends PaginatorGUIButton.Builder<Builder, PreviousPageGUIButton> {
        /**
         * Creates a new GUIButton
         *
         * @param gui The GUI
         */
        public Builder(GUI gui, PaginatorGUIPane paginator) {
            super(gui, paginator);
        }

        @Override
        public PreviousPageGUIButton build() {
            return new PreviousPageGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.getPaginator());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
