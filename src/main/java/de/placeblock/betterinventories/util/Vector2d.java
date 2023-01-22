package de.placeblock.betterinventories.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Author: Placeblock
 */
@Getter
@RequiredArgsConstructor
public class Vector2d {

    private final int x;
    private final int y;

    public Vector2d() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2d subtract(Vector2d vector2d) {
        return new Vector2d(this.x - vector2d.x, this.y - vector2d.y);
    }

    public Vector2d add(Vector2d vector2d) {
        return new Vector2d(this.x + vector2d.x, this.y + vector2d.y);
    }

    @Override
    public String toString() {
        return "Vector2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
