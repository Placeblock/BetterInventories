package de.placeblock.betterinventories;

import de.placeblock.betterinventories.util.Vector2d;

public interface Sizeable {

    Vector2d getSize();

    Vector2d getMaxSize();

    Vector2d getMinSize();

    default Vector2d clampSize(Vector2d vector2d) {
        return Vector2d.clamp(vector2d, this.getMinSize(), this.getMaxSize());
    }

}
