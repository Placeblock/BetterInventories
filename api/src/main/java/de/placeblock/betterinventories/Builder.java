package de.placeblock.betterinventories;

public abstract class Builder<B extends Builder<B, P>, P> {

    protected abstract B self();

    public abstract P build();

}
