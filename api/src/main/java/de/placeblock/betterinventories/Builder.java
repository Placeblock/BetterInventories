package de.placeblock.betterinventories;

/**
 * Interface for all sorts of builders
 * @param <B> The Builder that implements this one
 * @param <P> The Result that is produced
 */
public abstract class Builder<B extends Builder<B, P>, P> {
    /**
     * Returns the Builder instance itself
     * @return the Builder instance itself
     */
    protected abstract B self();

    /**
     * Builds the instance
     * @return The new instance
     */
    public abstract P build();

}
