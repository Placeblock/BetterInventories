package de.placeblock.betterinventories.builder;


@SuppressWarnings("unused")
public interface Builder<G, B extends Builder<G, B>> {

    G build();

}
