package de.placeblock.betterinventories.content.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryEvent;

/**
 * The Button receives the ClickData after an Interaction occurs
 */
@Getter
@RequiredArgsConstructor
public class ClickData {
    /**
     * The Player who clicked.
     */
    private final Player player;
    /**
     * The slot that got clicked
     */
    private final int slot;
    /**
     * The InventoryAction of the Event
     */
    private final InventoryAction action;
    /**
     * The Event
     */
    private final InventoryEvent event;

}
