package de.placeblock.betterinventories.modifier;

import de.placeblock.betterinventories.content.pane.impl.FramedGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;

@Getter
public class FrameGUITemplate extends GUITemplate {

    private final FramedGUIPane frame;

    public FrameGUITemplate(GUI gui) {
        super(gui);
        this.frame
    }

    @Override
    void setup() {

    }

}
