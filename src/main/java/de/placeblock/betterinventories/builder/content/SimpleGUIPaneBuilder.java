package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;


@SuppressWarnings("unused")
public class SimpleGUIPaneBuilder extends BaseGUIPaneBuilder<SimpleGUIPane, SimpleGUIPaneBuilder> {
    public SimpleGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    @Override
    public SimpleGUIPane build() {
        return new SimpleGUIPane(this.getGui(), getBestMinSize(), this.getBestMaxSize());
    }
}