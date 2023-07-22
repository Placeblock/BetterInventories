package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@SuppressWarnings("unused")
public abstract class GUIPane extends GUISection {

    public GUIPane(GUI gui, Vector2d size, Vector2d maxSize) {
        super(gui, size, maxSize);
    }

    public void setSize(Vector2d size) {
        boolean sizeChanged = !this.size.equals(size);
        this.size = size;
        if (sizeChanged) this.onSizeChange();
    }

    public void setHeight(int height) {
        this.setSize(new Vector2d(this.getWidth(), height));
    }

    public void setWidth(int width) {
        this.setSize(new Vector2d(width, this.getHeight()));
    }

    public void updateSizeRecursive(Vector2d parentMaxSize) {
        this.updateSize(parentMaxSize);
        for (GUISection child : this.getChildren()) {
            if (child instanceof GUIPane pane) {
                pane.updateSizeRecursive(parentMaxSize);
            }
        }
    }

    abstract public void updateSize(Vector2d parentMaxSize);

    public void onSizeChange() {

    }

    abstract public Set<GUISection> getChildren();
}
