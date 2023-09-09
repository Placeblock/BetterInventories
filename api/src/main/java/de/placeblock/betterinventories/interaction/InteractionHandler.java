package de.placeblock.betterinventories.interaction;

import de.placeblock.betterinventories.gui.GUI;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * InteractionHandlers are used to process Interactions in GUIs.
 * You can register them in a GUI using a {@link HandlerPriority}.
 * When an interaction occurs the InteractionHandler will get called.
 * If one Handler returns true other Handlers won't get executed anymore.
 */
@RequiredArgsConstructor
public abstract class InteractionHandler {
    /**
     * The according GUI
     */
    protected final GUI gui;

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