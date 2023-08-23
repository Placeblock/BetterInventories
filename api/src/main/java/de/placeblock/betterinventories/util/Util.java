package de.placeblock.betterinventories.util;

import java.util.Collection;
import java.util.List;


@SuppressWarnings("unused")
public class Util {
    /**
     * Can be used to calculate the size of an Inventory based on the amount of Items this Inventory should contain.
     * @param items The Items Array
     * @return The Inventory size
     */
    public static <T> Vector2d calculateGUISize(T[] items) {
        return Util.calculateGUISize(items, 9);
    }

    /**
     * Can be used to calculate the size of an Inventory based on the amount of Items this Inventory should contain.
     * @param items The Items Array
     * @param width The width of the GUI
     * @return The Inventory size
     */
    public static <T> Vector2d calculateGUISize(T[] items, int width) {
        return Util.calculateGUISize(List.of(items), width);
    }

    /**
     * Can be used to calculate the size of an Inventory based on the amount of Items this Inventory should contain.
     * @param items The Items Collection
     * @return The Inventory size
     */
    public static Vector2d calculateGUISize(Collection<?> items) {
        return Util.calculateGUISize(items, 9);
    }

    /**
     * Can be used to calculate the size of an Inventory based on the amount of Items this Inventory should contain.
     * @param items The Items Collection
     * @param width The width of the GUI
     * @return The Inventory size
     */
    public static Vector2d calculateGUISize(Collection<?> items, int width) {
        return new Vector2d(width, Math.min((int) Math.ceil(items.size()*1F/width), 6));
    }

    /**
     * Calculates the Vector given a slot and a width
     * @param slot The slot
     * @param width The width
     * @return The according Vector
     */
    public static Vector2d slotToVector(int slot, int width) {
        return new Vector2d(slot % width, (int) Math.floor(slot/(width*1F)));
    }

    /**
     * Calculates the slot given a Vector and a width
     * @param vector The Vector
     * @param width The width
     * @return The according slot
     */
    public static int vectorToSlot(Vector2d vector, int width) {
        return vector.getY()*width+vector.getX();
    }

    /**
     * Modulo that also works for negative numbers
     * @param dividend The dividend
     * @param divisor The divisor
     * @return The modulo
     */
    public static int modulo(int dividend, int divisor) {
        return (((dividend % divisor) + divisor) % divisor);
    }
}
