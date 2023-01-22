package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;

/**
 * Author: Placeblock
 */
public abstract class BaseGUIPaneBuilder<G extends GUIPane, B extends BaseGUIPaneBuilder<G, B>> extends BaseGUISectionBuilder<G, B>{
    public BaseGUIPaneBuilder(GUI gui) {
        super(gui);
    }
}
