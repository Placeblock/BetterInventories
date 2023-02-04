package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * Author: Placeblock
 */
@SuppressWarnings({"unchecked", "unused"})
public abstract class BaseGUIPaneBuilder<G extends GUIPane, B extends BaseGUIPaneBuilder<G, B>> extends BaseGUISectionBuilder<G, B>{
    private Vector2d maxSize;
    private Vector2d minSize;
    private boolean autoSize = false;

    public BaseGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    public B maxSize(Vector2d maxSize) {
        this.maxSize = maxSize;
        return (B) this;
    }

    public B minSize(Vector2d minSize) {
        this.minSize = minSize;
        return (B) this;
    }

    public B autoSize() {
        this.autoSize = true;
        return (B) this;
    }

    protected Vector2d getMaxSize() {
        return this.maxSize == null ? this.getSize() : this.maxSize;
    }

    protected Vector2d getMinSize() {
        return this.minSize == null ? this.getSize() : this.minSize;
    }

    protected boolean getAutoSize() {
        return this.autoSize;
    }
}
