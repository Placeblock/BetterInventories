package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link GUIButton} which automatically teleports the player to a specific {@link Location}
 */
@SuppressWarnings("unused")
public class TeleportGUIButton extends GUIButton {
    private final Location location;

    public TeleportGUIButton(GUI gui, ItemStack item, Location location) {
        super(gui, item);
        this.location = location;
    }

    @Override
    public void onClick(ClickData data) {
        Player player = data.getPlayer();
        player.closeInventory();
        player.teleport(location);
    }
}