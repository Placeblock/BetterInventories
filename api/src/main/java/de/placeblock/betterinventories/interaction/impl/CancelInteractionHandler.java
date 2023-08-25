package de.placeblock.betterinventories.interaction.impl;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.interaction.InteractionHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * InteractionHandler for cancelling GUI interaction
 */
public class CancelInteractionHandler extends InteractionHandler {
    /**
     * Creates a new CancelInteractionHandler
     * @param gui The according GUI
     */
    public CancelInteractionHandler(GUI gui) {
        super(gui);
    }

    /**
     * Called on-click
     * @param event The Event
     * @return Whether to stop handler-calling
     */
    @Override
    public boolean onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        return false;
    }

    /**
     * Called on-drag
     * @param event The Event
     * @return Whether to stop handler-calling
     */
    @Override
    public boolean onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
        return false;
    }
}
