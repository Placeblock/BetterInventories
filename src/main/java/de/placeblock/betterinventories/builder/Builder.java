package de.placeblock.betterinventories.builder;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public interface Builder<G, B extends Builder<G, B>> {

    G build();

}
