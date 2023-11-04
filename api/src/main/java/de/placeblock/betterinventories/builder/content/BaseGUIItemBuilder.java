package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;

/**
 * Base class for creating GUIITem-Builders
 * @param <T> The GUIItem type
 * @param <B> The Builder type
 */
@Deprecated(forRemoval = true)
@SuppressWarnings("unchecked")
public abstract class BaseGUIItemBuilder<T extends GUIItem, B extends BaseGUIItemBuilder<T, B>> extends BaseGUISectionBuilder<T, B> {
    /**
     * The ItemStack of the GUIItem
     */
    private ItemStack item;

    /**
     * Creates a new BaseGUIItemBuilder
     * @param gui The GUI for the Item
     */
    public BaseGUIItemBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Sets the ItemStack of the GUIItem
     * @param item The ItemStack
     * @return this
     */
    public B item(ItemStack item) {
        this.item = item;
        return (B) this;
    }

    /**
     * @return The ItemStack of the GUIItem
     */
    protected ItemStack getItem() {
        return this.getValue(this.item);
    }
}
