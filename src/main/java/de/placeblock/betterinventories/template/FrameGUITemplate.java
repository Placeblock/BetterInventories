package de.placeblock.betterinventories.template;

import com.destroystokyo.paper.util.RedstoneWireTurbo;
import de.placeblock.betterinventories.content.pane.SimpleGUIPane;
import de.placeblock.betterinventories.content.pane.impl.FramedGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.CanvasGUI;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class FrameGUITemplate extends GUITemplate<CanvasGUI> {

    private FramedGUIPane frame;

    public FrameGUITemplate(CanvasGUI gui) {
        super(gui);
    }

    public void setup(boolean vertical, Supplier<GUI> backGUI) {
        this.setup(this.gui.getCanvas(), vertical, backGUI);
    }

    public void setup(SimpleGUIPane pane, boolean vertical, Supplier<GUI> backGUI) {
        this.frame = new FramedGUIPane(this.gui, this.gui.getCanvas().getSize(), vertical, backGUI);
        pane.setSectionAt(0, this.frame);
    }

}
