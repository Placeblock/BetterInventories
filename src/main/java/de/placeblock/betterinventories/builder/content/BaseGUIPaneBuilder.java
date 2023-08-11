package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;

import java.util.List;


@Getter
@SuppressWarnings({"unchecked", "unused"})
public abstract class BaseGUIPaneBuilder<G extends GUIPane, B extends BaseGUIPaneBuilder<G, B>> extends BaseGUISectionBuilder<G, B>{
    private Vector2d maxSize;
    private Vector2d minSize;

    public BaseGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Sets the maxSize of the pane.
     * If no maxSize is set at build-time it will use minSize or Size
     */
    public B maxSize(int x, int y) {
        return this.maxSize(new Vector2d(x, y));
    }

    /**
     * Sets the maxSize of the pane.
     * If no maxSize is set at build-time it will use minSize or Size
     */
    public B maxSize(Vector2d maxSize) {
        this.maxSize = maxSize;
        return (B) this;
    }

    /**
     * Sets the minSize of the pane.
     * If no minSize is set at build-time it will use Size or (1,1)
     */
    public B minSize(int x, int y) {
        return this.minSize(new Vector2d(x, y));
    }

    /**
     * Sets the minSize of the pane.
     * If no minSize is set at build-time it will use Size or (1,1)
     */
    public B minSize(Vector2d minSize) {
        this.minSize = minSize;
        return (B) this;
    }

    /**
     * Sets the min and mix size according to the min and max size of the {@link GUIPane}
     * @param pane The GUIPane from which the size should be taken
     */
    public B adoptMinMax(GUIPane pane) {
        this.minSize = pane.getMinSize();
        this.maxSize = pane.getMaxSize();
        return (B) this;
    }

    protected Vector2d getBestMaxSize() {
        return this.getValue(List.of(this::getMaxSize, this::getMinSize, this::getSize));
    }

    protected Vector2d getBestMinSize() {
        return this.getValue(List.of(this::getMinSize, this::getSize, () -> new Vector2d(1, 1)));
    }
}
