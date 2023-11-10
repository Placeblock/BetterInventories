package de.placeblock.betterinventories.gui.listener;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.GUIView;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


/**
 * Listener for GUIs that handle gui stuff.
 * Registered in the GUI itself.
 */
@RequiredArgsConstructor
public class GUIListener implements Listener {

    private final GUI gui;

    /**
     * Called by Bukkit when a Player closes an Inventory
     * @param event The Event
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        GUIView view = this.gui.getView(event.getInventory());
        if (view != null) {
            this.gui.removePlayer(view);
        }
    }
}
