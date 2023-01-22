package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SimpleGUIPaneBuilder extends BaseGUIPaneBuilder<SimpleGUIPane, SimpleGUIPaneBuilder> {
    public SimpleGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    @Override
    public SimpleGUIPane build() {
        return new SimpleGUIPane(this.getGui(), this.getWidth(), this.getHeight());
    }
}
