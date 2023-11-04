package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.content.item.BaseGUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;

import java.util.Collection;
import java.util.function.Function;

/**
 * Implementation of {@link BaseSimpleGUIPane} that can contain only {@link BaseGUIItem}s.
 */
@SuppressWarnings("unused")
public class SimpleItemGUIPane extends BaseSimpleGUIPane<BaseGUIItem, SimpleItemGUIPane> {

    /**
     * Creates a new SimpleGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    @Deprecated(forRemoval = true)
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
        for (ChildData<BaseGUIItem> childData : this.content) {
            Vector2d newPos = transformation.apply(childData.getPosition());
            childData.setPosition(newPos);
        }
    }

    /**
     * Returns an item for a given slot
     * @param slot The slot
     * @return The item or null
     */
    public BaseGUIItem getItem(int slot) {
        return this.getItem(this.slotToVector(slot));
    }

    /**
     * Returns an item for a given position
     * @param position The position
     * @return The item or null
     */
    public BaseGUIItem getItem(Vector2d position) {
        Collection<BaseGUIItem> sections = this.getSections(position);
        for (BaseGUIItem section : sections) {
            return section;
        }
        return null;
    }


    public static class Builder extends BaseSimpleGUIPane.Builder<Builder, SimpleItemGUIPane> {
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public SimpleItemGUIPane build() {
            return new SimpleItemGUIPane(this.getGui(), this.getMinSize(), this.getMaxSize(), this.isAutoSize());
        }
        @Override
        protected SimpleItemGUIPane.Builder self() {
            return this;
        }
    }
}
