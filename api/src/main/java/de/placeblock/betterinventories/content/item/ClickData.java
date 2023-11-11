package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * The Button receives the ClickData after an Interaction occurs
 *
 * @param player   The Player who clicked.
 * @param position The relative position of the slot that got clicked
 * @param action   The InventoryAction of the Event
 * @param event    The Event
 */
public record ClickData(Player player, Vector2d position, InventoryAction action, InventoryClickEvent event) {
}
