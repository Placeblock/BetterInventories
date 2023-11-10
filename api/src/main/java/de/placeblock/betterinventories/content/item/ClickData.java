package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

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
     * The relative position of the slot that got clicked
     */
    private final Vector2d position;
    /**
     * The InventoryAction of the Event
     */
    private final InventoryAction action;
    /**
     * The Event
     */
    private final InventoryClickEvent event;

}
