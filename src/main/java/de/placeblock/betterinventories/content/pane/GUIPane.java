package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@SuppressWarnings("unused")
public abstract class GUIPane extends GUISection {

    public GUIPane(GUI gui, Vector2d size, Vector2d maxSize) {
        super(gui, size, maxSize);
    }

    public void setSize(Vector2d size) {
        this.size = size;
    }

    public void setHeight(int height) {
        this.setSize(new Vector2d(this.getWidth(), height));
    }

    public void setWidth(int width) {
        this.setSize(new Vector2d(width, this.getHeight()));
    }

    abstract void updateSize(Vector2d parentMaxSize);
}
