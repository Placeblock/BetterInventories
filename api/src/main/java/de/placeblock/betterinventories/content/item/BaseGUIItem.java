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
public abstract class BaseGUIItem extends GUISection {
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
    public BaseGUIItem(GUI gui, ItemStack itemStack) {
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

    @Getter(AccessLevel.PROTECTED)
    public static abstract class Builder<B extends Builder<B, P>, P extends BaseGUIItem> extends GUISection.Builder<B, P> {
        private ItemStack itemStack;

        public Builder(GUI gui) {
            super(gui);
        }

        public B itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this.self();
        }
    }
}
