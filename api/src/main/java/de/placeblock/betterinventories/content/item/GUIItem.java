package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link GUISection} with a size of 1x1 containing an {@link ItemStack}
 */
@Getter
@SuppressWarnings("unused")
public class GUIItem extends GUISection {
    /**
     * The Size of every GUIItem
     */
    public static final Vector2d BUTTON_SIZE = new Vector2d(1, 1);
    /**
     * The ItemStack to be rendered
     */
    @Setter
    protected ItemStack itemStack;

    /**
     * Creates a new GUIItem
     * @param gui The GUI
     * @param itemStack The ItemStack. {@link ItemBuilder} may help to create it.
     */
    protected GUIItem(GUI gui, ItemStack itemStack) {
        super(gui, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
        this.itemStack = itemStack;
    }

    /**
     * Renders the GUIItem on a list
     * @return The List
     */
    @Override
    public List<ItemStack> render() {
        return new ArrayList<>(List.of(itemStack));
    }

    /**
     * Searches the GUISection recursively. The SearchData is filled recursively.
     * @param searchData The searchData that contains all needed information
     */
    @Override
    public void search(SearchData searchData) {
        searchData.setRelativePos(new Vector2d());
        searchData.setSection(this);
    }


    @Override
    public void onItemClick(ClickData data) {}

    @Override
    public boolean onItemAdd(Vector2d position, ItemStack itemStack) {
        return true;
    }

    @Override
    public ItemStack onItemRemove(Vector2d position) {
        return null;
    }

    @Override
    public boolean onItemAmount(Vector2d position, int amount) {
        return true;
    }

    /**
     * Abstract Builder for creating various GUIItems
     * @param <B> The Builder that implements this one
     * @param <P> The {@link GUIItem} that is built
     */
    @Getter(AccessLevel.PROTECTED)
    protected static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends GUIItem> extends GUISection.AbstractBuilder<B, P> {
        private ItemStack itemStack;

        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public AbstractBuilder(GUI gui) {
            super(gui);
        }

        /**
         * Sets the itemStack attribute
         * @param itemStack The itemStack that sits inside this {@link GUIItem}
         * @return Itself
         */
        public B itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this.self();
        }
    }

    /**
     * Builder for creating {@link GUIItem}
     */
    public static class Builder extends AbstractBuilder<Builder, GUIItem> {
        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public GUIItem build() {
            return new GUIItem(this.getGui(), this.getItemStack());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
