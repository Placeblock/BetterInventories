package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;

import java.util.List;

/**
 * Base class for creating GUIPane-Builders
 * @param <G> The GUIPane type
 * @param <B> The Builder type
 */
@Deprecated(forRemoval = true)
@Getter
@SuppressWarnings({"unchecked", "unused"})
public abstract class BaseGUIPaneBuilder<G extends GUIPane, B extends BaseGUIPaneBuilder<G, B>> extends BaseGUISectionBuilder<G, B>{
    /**
     * The maximum size of the Pane
     */
    private Vector2d maxSize;

    /**
     * The minimum size of the Pane
     */
    private Vector2d minSize;

    /**
     * Creates a new BaseGUIPaneBuilder
     * @param gui The GUI for the Pane
     */
    public BaseGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Sets the maxSize of the pane.
     * If no maxSize is set at build-time it will use minSize or Size
     * @param x The maximum width
     * @param y The maximum height
     * @return this
     */
    public B maxSize(int x, int y) {
        return this.maxSize(new Vector2d(x, y));
    }

    /**
     * Sets the maximum size of the pane.
     * If no maximum size is set at build-time it will use the minimum size or size
     * @param maxSize The maximum size
     * @return this
     */
    public B maxSize(Vector2d maxSize) {
        this.maxSize = maxSize;
        return (B) this;
    }

    /**
     * Sets the minimum size of the pane.
     * If no minimum size is set at build-time it will use size or (1,1)
     * @param x The minimum width
     * @param y The minimum height
     * @return this
     */
    public B minSize(int x, int y) {
        return this.minSize(new Vector2d(x, y));
    }

    /**
     * Sets the minimum size of the pane.
     * If no minimum size is set at build-time it will use size or (1, 1)
     * @param minSize The minmum size
     * @return this
     */
    public B minSize(Vector2d minSize) {
        this.minSize = minSize;
        return (B) this;
    }

    /**
     * Sets the min and mix size according to the min and max size of the {@link GUIPane}
     * @param pane The GUIPane from which the size should be taken
     * @return this
     */
    public B adoptMinMax(GUIPane pane) {
        this.minSize = pane.getMinSize();
        this.maxSize = pane.getMaxSize();
        return (B) this;
    }

    /**
     * @return The best maximum size (maxSize -> minSize -> size)
     */
    protected Vector2d getBestMaxSize() {
        return this.getValue(List.of(this::getMaxSize, this::getMinSize, this::getSize));
    }

    /**
     * @return The best minimum size (minSize -> size -> (1,1))
     */
    protected Vector2d getBestMinSize() {
        return this.getValue(List.of(this::getMinSize, this::getSize, () -> new Vector2d(1, 1)));
    }
}
