package de.placeblock.betterinventories.content.item.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.impl.CommandGUIButton;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link GUIButton} for the {@link PaginatorGUIPane} which skips to the next page.
 */
public class NextPageGUIButton extends PaginatorGUIButton {
    /**
     * Creates a new NextPageGUIButton
     * @param paginatorGUIPane The according paginator
     * @param gui The GUI
     * @param cooldown The cooldown of the Button
     * @param permission The permission required to press this button
     * @param sound The sound played when pressing this button
     * @param item The ItemStack of the Button
     */
    protected NextPageGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, PaginatorGUIPane paginatorGUIPane) {
        super(gui, item, cooldown, sound, permission, paginatorGUIPane);
    }

    @Override
    public void onClick(ClickData data) {
        this.paginatorGUIPane.nextPage();
        this.getGui().update();
    }

    /**
     * Abstract Builder for creating {@link NextPageGUIButton}
     * @param <B> The Builder that implements this one
     * @param <P> The {@link CommandGUIButton} that is build
     */
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends NextPageGUIButton> extends PaginatorGUIButton.AbstractBuilder<B, P> {
        /**
         * Creates a new Builder
         *
         * @param gui       The GUI
         * @param paginator The paginator this button belongs to
         */
        protected AbstractBuilder(GUI gui, PaginatorGUIPane paginator) {
            super(gui, paginator);
        }
    }

    /**
     * Builder for creating {@link NextPageGUIButton}
     */
    public static class Builder extends AbstractBuilder<Builder, NextPageGUIButton> {
        /**
         * Creates a new GUIButton
         * @param gui The GUI
         * @param paginator The paginator this button belongs to
         */
        public Builder(GUI gui, PaginatorGUIPane paginator) {
            super(gui, paginator);
        }

        @Override
        public NextPageGUIButton build() {
            return new NextPageGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.getPaginator());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
