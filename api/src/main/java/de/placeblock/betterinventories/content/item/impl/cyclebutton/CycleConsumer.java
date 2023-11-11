package de.placeblock.betterinventories.content.item.impl.cyclebutton;

import de.placeblock.betterinventories.content.item.ClickData;

/**
 * Used by the {@link CycleGUIButton}
 * @param <E> The type of the enum
 */
@FunctionalInterface
public interface CycleConsumer<E extends CycleEnum> {
    /**
     * Executed when the button is cycled
     * @param clickData The clickData of the event
     * @param value The new enum value
     */
    void apply(ClickData clickData, E value);
}
