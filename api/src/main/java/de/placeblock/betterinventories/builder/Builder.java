package de.placeblock.betterinventories.builder;


import java.util.List;
import java.util.function.Supplier;

/**
 * Interface for creating Builders
 * @param <G> The type of Object to build
 * @param <B> The Builder type
 */
@SuppressWarnings("unused")
public interface Builder<G, B extends Builder<G, B>> {
    /**
     * This Exception gets called if there are missing values when building the object
     */
    IllegalStateException VALUE_MISSING_EXCEPTION = new IllegalStateException("Required value not found in Builder");

    /**
     * Builds the object
     * @return The built object
     */
    G build();

    /**
     * Gets a value from a supplier
     * @param location The supplier
     * @return The value
     * @param <T> The type of value
     */
    default <T> T getValue(Supplier<T> location) {
        return this.getValue(location.get());
    }

    /**
     * Returns a value if not null
     * @param value The value
     * @return The value
     * @param <T> The type of value
     */
    default <T> T getValue(T value) {
        if (value != null) {
            return value;
        } else {
            throw VALUE_MISSING_EXCEPTION;
        }
    }

    /**
     * Returns the first non-null value
     * @param locations The values
     * @return The value
     * @param <T> The type of the values
     */
    default <T> T getValue(List<Supplier<T>> locations) {
        for (Supplier<T> location : locations) {
            T value = location.get();
            if (value != null) {
                return value;
            }
        }
        throw VALUE_MISSING_EXCEPTION;
    }

}
