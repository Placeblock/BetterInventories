package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class GUIItemBuilder extends BaseGUIItemBuilder<GUIItem, GUIItemBuilder> {
    public GUIItemBuilder(GUI gui) {
        super(gui);
    }

    @Override
    public GUIItem build() {
        return new GUIItem(this.getGui(), this.getItem());
    }
}
