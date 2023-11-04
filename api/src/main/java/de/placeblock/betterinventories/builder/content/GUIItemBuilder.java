package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;

/**
 * Builder for GUIItems
 */
@Deprecated(forRemoval = true)
@SuppressWarnings("unused")
public class GUIItemBuilder extends BaseGUIItemBuilder<GUIItem, GUIItemBuilder> {
    /**
     * Creates a new GUIItemBuilder
     * @param gui The GUI for the Item
     */
    public GUIItemBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Builds the GUIItem
     * @return The new GUIItem
     */
    @Override
    public GUIItem build() {
        return new GUIItem(this.getGui(), this.getItem());
    }
}
