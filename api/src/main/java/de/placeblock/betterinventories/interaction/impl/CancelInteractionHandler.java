package de.placeblock.betterinventories.interaction.impl;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.interaction.InteractionHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class CancelInteractionHandler extends InteractionHandler {
    public CancelInteractionHandler(GUI gui) {
        super(gui);
    }

    @Override
    public boolean onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        return false;
    }

    @Override
    public boolean onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
        return false;
    }
}
