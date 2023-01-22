package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unchecked")
public abstract class BaseGUIItemBuilder<T extends GUIItem, B extends BaseGUIItemBuilder<T, B>> extends BaseGUISectionBuilder<T, B> {
    private ItemStack item;

    public BaseGUIItemBuilder(GUI gui) {
        super(gui);
    }

    public B item(ItemStack item) {
        this.item = item;
        return (B) this;
    }

    protected ItemStack getItem() {
        if (this.item == null) {
            throw new IllegalStateException("Item is null in builder");
        }
        return this.item;
    }
}
