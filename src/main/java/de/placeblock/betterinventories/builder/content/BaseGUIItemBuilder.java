package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;


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
        return this.getValue(this.item);
    }
}
