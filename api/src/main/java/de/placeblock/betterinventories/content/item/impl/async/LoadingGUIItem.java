package de.placeblock.betterinventories.content.item.impl.async;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.item.impl.SwitchGUIButton;
import de.placeblock.betterinventories.content.pane.impl.async.BaseAsyncGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

/**
 * Default LoadingGUIItem used by the {@link BaseAsyncGUIPane}
 */
public class LoadingGUIItem extends GUIItem {
    /**
     * Creates a new LoadingGUIItem
     *
     * @param gui       The GUI
     */
    protected LoadingGUIItem(GUI gui) {
        super(gui, new ItemBuilder(Component.text("Läd..."),
                Material.HEART_OF_THE_SEA).build());
    }

    /**
     * Abstract Builder for creating {@link SwitchGUIButton}
     * @param <B> The Builder that implements this one
     * @param <P> The {@link GUIButton} that is built
     */
    @Getter(AccessLevel.PROTECTED)
    protected static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends LoadingGUIItem> extends GUIItem.AbstractBuilder<B, P> {

        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        protected AbstractBuilder(GUI gui) {
            super(gui);
        }
    }

    /**
     * Builder for creating {@link SwitchGUIButton}
     */
    public static class Builder extends LoadingGUIItem.AbstractBuilder<LoadingGUIItem.Builder, LoadingGUIItem> {
        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public LoadingGUIItem build() {
            return new LoadingGUIItem(this.getGui());
        }

        @Override
        protected LoadingGUIItem.Builder self() {
            return this;
        }
    }
}
