package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.builder.content.SimpleItemGUIPaneBuilder;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;

import java.util.function.Function;

/**
 * Implementation of {@link BaseSimpleGUIPane} that can contain only {@link GUIItem}s.
 * <br>
 * Builder: {@link SimpleItemGUIPaneBuilder}
 */
@SuppressWarnings("unused")
public class SimpleItemGUIPane extends BaseSimpleGUIPane<GUIItem, SimpleItemGUIPane> {
    /**
     * Creates a new SimpleGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     */
    public SimpleItemGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        this(gui, minSize, maxSize, false);
    }
    /**
     * Creates a new SimpleGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public SimpleItemGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
    }

    /**
     * Flips all items on the Y axis.
     * Items on the left will be on the right and vice versa.
     */
    public void flipY() {
        this.applyTransformation(v -> {
            int spaceLeft = v.getX() + 1;
            return new Vector2d(this.getWidth()-spaceLeft, v.getY());
        });
    }

    /**
     * Flips all items on the X axis.
     * Items on the top will be on the bottom and vice versa.
     */
    public void flipX() {
        this.applyTransformation(v -> {
            int spaceTop = v.getY() + 1;
            return new Vector2d(v.getX(), this.getHeight()-spaceTop);
        });
    }

    /**
     * Shifts Items to the left or right
     * @param delta The delta (positive to shift to the right)
     */
    public void shiftX(int delta) {
        this.applyTransformation(v -> {
            if (v.getX() >= 0 && v.getX() < this.getWidth()) {
                int newX = Util.modulo((v.getX() + delta),this.getWidth());
                return new Vector2d(newX, v.getY());
            }
            return v;
        });
    }

    /**
     * Shifts Items to the top or bottom
     * @param delta The delta (positive to shift to the bottom)
     */
    public void shiftY(int delta) {
        this.applyTransformation(v -> {
            if (v.getY() >= 0 && v.getY() < this.getHeight()) {
                int newY = Util.modulo((v.getY()+delta),this.getHeight());
                return new Vector2d(v.getX(), newY);
            }
            return v;
        });
    }

    /**
     * Used to apply transformations to all children
     * @param transformation The transformation
     */
    private void applyTransformation(Function<Vector2d, Vector2d> transformation) {
        for (ChildData<GUIItem> childData : this.content) {
            Vector2d newPos = transformation.apply(childData.getPosition());
            childData.setPosition(newPos);
        }
    }
}
