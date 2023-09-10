package de.placeblock.betterinventories.interaction;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * InteractionHandlers are used to process Interactions in GUIs.
 * You can register them in a GUI or GUISections.
 * When an interaction occurs the InteractionHandler will get called.
 * If one Handler returns true other Handlers won't get executed anymore.
 */
@RequiredArgsConstructor
public abstract class InteractionHandler {
    /**
     * Called on-click
     * @param event The Event
     * @return Whether to stop handler-calling
     */
    public abstract boolean onClick(InventoryClickEvent event);

    /**
     * Called on-drag
     * @param event The Event
     * @return Whether to stop handler-calling
     */
    public abstract boolean onDrag(InventoryDragEvent event);

}
