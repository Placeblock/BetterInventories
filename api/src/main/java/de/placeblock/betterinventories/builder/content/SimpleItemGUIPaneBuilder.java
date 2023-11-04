package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;

/**
 * Builder for SimpleItemGUIPanes
 */
@Deprecated(forRemoval = true)
@SuppressWarnings("unused")
public class SimpleItemGUIPaneBuilder extends BaseGUIPaneBuilder<SimpleItemGUIPane, SimpleItemGUIPaneBuilder> {
    /**
     * Creates a new SimpleItemGUIPaneBuilder
     * @param gui The GUI for the Pane
     */
    public SimpleItemGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Builds the Pane
     * @return The new Pane
     */
    @Override
    public SimpleItemGUIPane build() {
        return new SimpleItemGUIPane(this.getGui(), getBestMinSize(), this.getBestMaxSize());
    }
}
