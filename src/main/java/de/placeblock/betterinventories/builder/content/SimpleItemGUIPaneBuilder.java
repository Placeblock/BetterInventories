package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;

@SuppressWarnings("unused")
public class SimpleItemGUIPaneBuilder extends BaseGUIPaneBuilder<SimpleItemGUIPane, SimpleItemGUIPaneBuilder> {
    public SimpleItemGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    @Override
    public SimpleItemGUIPane build() {
        return new SimpleItemGUIPane(this.getGui(), getBestMinSize(), this.getBestMaxSize());
    }
}
