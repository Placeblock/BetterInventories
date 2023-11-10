package de.placeblock.betterinventories.content.item.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class for {@link GUIButton}s that belong to a specific {@link PaginatorGUIPane}.
 */
public abstract class PaginatorGUIButton extends GUIButton {

    /**
     * The according Paginator
     */
    protected final PaginatorGUIPane paginatorGUIPane;

    /**
     * Creates a new PaginatorGUIButton
     * @param gui The GUI
     * @param paginatorGUIPane The according Paginator
     * @param item The ItemStack of the Button
     * @param cooldown The cooldown of the Button
     * @param permission The permission required to press this button
     * @param sound The sound played when pressing this button
     */
    public PaginatorGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound,
                              String permission, PaginatorGUIPane paginatorGUIPane) {
        super(gui, item, cooldown, sound, permission);
        this.paginatorGUIPane = paginatorGUIPane;
    }

    /**
     * Abstract Builder for creating various {@link PaginatorGUIButton}
     * @param <B> The Builder that implements this one
     * @param <P> The {@link GUIButton} that is built
     */
    @Getter(AccessLevel.PROTECTED)
    public static abstract class Builder<B extends Builder<B, P>, P extends PaginatorGUIButton> extends AbstractBuilder<B, P> {
        private final PaginatorGUIPane paginator;

        /**
         * Creates a new GUIButton
         * @param gui The GUI
         * @param paginator The paginator this button belongs to
         */
        public Builder(GUI gui, PaginatorGUIPane paginator) {
            super(gui);
            this.paginator = paginator;
        }
    }

}
