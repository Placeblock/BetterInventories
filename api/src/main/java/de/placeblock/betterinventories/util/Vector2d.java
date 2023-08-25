package de.placeblock.betterinventories.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;

/**
 * Vector class to specify positions in GUIs
 */
@Getter
@RequiredArgsConstructor
public class Vector2d {
    /**
     * The x-coordinate
     */
    private final int x;
    /**
     * The y-coordinate
     */
    private final int y;

    /**
     * Creates a new Vector with 0-values
     */
    public Vector2d() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Subtract a Vector from this Vector
     * @param vector2d The Vector
     * @return The new Vector
     */
    public Vector2d subtract(Vector2d vector2d) {
        return new Vector2d(this.x - vector2d.x, this.y - vector2d.y);
    }

    /**
     * Adds a Vector to this Vector
     * @param vector2d The Vector
     * @return The new Vector
     */
    public Vector2d add(Vector2d vector2d) {
        return new Vector2d(this.x + vector2d.x, this.y + vector2d.y);
    }

    /**
     * New equals method comparing just x and y
     * @param o The object
     * @return True if both objects are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    /**
     * @return The hash-code of the Vector
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @return The string representation of the Vector
     */
    @Override
    public String toString() {
        return "Vector2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Calculates the largest Vector
     * @param vectors The Vectors
     * @return The largest Vector
     */
    public static Vector2d largest(Collection<Vector2d> vectors) {
        Vector2d largest = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (Vector2d vector : vectors) {
            largest = max(vector, largest);
        }
        return largest;
    }

    /**
     * Calculates the maximum of two Vectors
     * @param vector1 The first Vector
     * @param vector2 The second Vector
     * @return The new Vector
     */
    public static Vector2d max(Vector2d vector1, Vector2d vector2) {
        int x = Math.max(vector1.x, vector2.x);
        int y = Math.max(vector1.y, vector2.y);
        return new Vector2d(x, y);
    }

    /**
     * Calculates the minimum of two Vectors
     * @param vector1 The first Vector
     * @param vector2 The second Vector
     * @return The new Vector
     */
    public static Vector2d min(Vector2d vector1, Vector2d vector2) {
        int x = Math.min(vector1.x, vector2.x);
        int y = Math.min(vector1.y, vector2.y);
        return new Vector2d(x, y);
    }

    /**
     * Clamps a Vector to two Vectors
     * @param vector The Vector to clamp
     * @param min The minimum Vector
     * @param max The maximum Vector
     * @return The clamped Vector
     */
    public static Vector2d clamp(Vector2d vector, Vector2d min, Vector2d max) {
        return new Vector2d(
                Math.min(Math.max(vector.getX(), min.getX()), max.getX()),
                Math.min(Math.max(vector.getY(), min.getY()), max.getY())
        );
    }
}
