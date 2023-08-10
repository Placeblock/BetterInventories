package de.placeblock.betterinventories.builder;


import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface Builder<G, B extends Builder<G, B>> {

    IllegalStateException VALUE_MISSING_EXCEPTION = new IllegalStateException("Required value not found in Builder");

    G build();

    default <T> T getValue(Supplier<T> location) {
        T value = location.get();
        if (value != null) {
            return value;
        } else {
            throw VALUE_MISSING_EXCEPTION;
        }
    }

    default <T> T getValue(T value) {
        if (value != null) {
            return value;
        } else {
            throw VALUE_MISSING_EXCEPTION;
        }
    }

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
