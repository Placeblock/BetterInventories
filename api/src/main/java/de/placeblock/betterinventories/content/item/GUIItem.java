package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;

public class GUIItem extends BaseGUIItem {
    /**
     * Creates a new GUIItem
     *
     * @param gui       The GUI
     * @param itemStack The ItemStack. {@link de.placeblock.betterinventories.util.ItemBuilder} may help to create it.
     */
    public GUIItem(GUI gui, ItemStack itemStack) {
        super(gui, itemStack);
    }

    public static class Builder extends BaseGUIItem.Builder<Builder, GUIItem> {
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
