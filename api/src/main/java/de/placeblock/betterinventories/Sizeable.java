package de.placeblock.betterinventories;

import de.placeblock.betterinventories.util.Vector2d;

/**
 * Used by GUIs and GUISections for sizing
 */
public interface Sizeable {
    /**
     * @return The size
     */
    Vector2d getSize();

    /**
     * @return The maximum size
     */
    Vector2d getMaxSize();

    /**
     * @return The minimum size
     */
    Vector2d getMinSize();

    /**
     * @param vector The Vector to clamp
     * @return Clamps a Vector to the minimum and maximum
     */
    default Vector2d clampSize(Vector2d vector) {
        return Vector2d.clamp(vector, this.getMinSize(), this.getMaxSize());
    }

}
