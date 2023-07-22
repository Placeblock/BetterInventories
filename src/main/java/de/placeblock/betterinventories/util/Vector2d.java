package de.placeblock.betterinventories.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static Vector2d largest(Collection<Vector2d> vectors) {
        Vector2d largest = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (Vector2d vector : vectors) {
            largest = max(vector, largest);
        }
        return largest;
    }

    public static Vector2d max(Vector2d vector1, Vector2d vector2) {
        int x = Math.max(vector1.x, vector2.x);
        int y = Math.max(vector1.y, vector2.y);
        return new Vector2d(x, y);
    }

    public static Vector2d min(Vector2d vector1, Vector2d vector2) {
        int x = Math.min(vector1.x, vector2.x);
        int y = Math.min(vector1.y, vector2.y);
        return new Vector2d(x, y);
    }
}
