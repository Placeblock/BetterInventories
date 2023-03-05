package de.placeblock.betterinventories.content.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryEvent;

@Getter
@RequiredArgsConstructor
public class ClickData {

    private final Player player;
    private final int slot;
    private final InventoryAction action;
    private final InventoryEvent event;

}
