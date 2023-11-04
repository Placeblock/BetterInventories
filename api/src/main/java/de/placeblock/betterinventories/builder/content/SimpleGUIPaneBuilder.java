package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.GUI;

/**
 * Builder for creating SimpleGUIPanes
 */
@Deprecated(forRemoval = true)
@SuppressWarnings("unused")
public class SimpleGUIPaneBuilder extends BaseGUIPaneBuilder<SimpleGUIPane, SimpleGUIPaneBuilder> {
    /**
     * Creates a new SimpleGUIPaneBuilder
     * @param gui The GUI for the Pane
     */
    public SimpleGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Builds the Pane
     * @return The new Pane
     */
    @Override
    public SimpleGUIPane build() {
        return new SimpleGUIPane(this.getGui(), getBestMinSize(), this.getBestMaxSize());
    }
}
