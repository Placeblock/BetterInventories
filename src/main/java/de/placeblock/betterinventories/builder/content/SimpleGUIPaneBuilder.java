package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;


@SuppressWarnings("unused")
public class SimpleGUIPaneBuilder extends BaseGUIPaneBuilder<SimpleGUIPane, SimpleGUIPaneBuilder> {
    public SimpleGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    @Override
    public SimpleGUIPane build() {
        Vector2d maxSize = this.getMaxSize() == null ? this.getSize() : this.getMaxSize();
        return new SimpleGUIPane(this.getGui(), this.getSize(), maxSize);
    }
}
