package de.placeblock.betterinventories.content.item.impl.togglebutton;

import de.placeblock.betterinventories.content.item.ClickData;

/**
 * Used by the {@link ToggleGUIButton}
 */
@FunctionalInterface
public interface ToggleConsumer {
    /**
     * Executed when the button is toggled
     * @param clickData The clickData of the event
     * @param toggled Whether the button is now toggled
     */
    void apply(ClickData clickData, boolean toggled);
}
