package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.BaseGUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link BaseGUIButton} which automatically teleports the player to a specific {@link Location}
 */
@SuppressWarnings("unused")
public class TeleportGUIButton extends BaseGUIButton {
    /**
     * The location to which the player is teleported
     */
    private final Location location;

    /**
     * Creates a new TeleportGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param location The location to which the player is teleported
     */
    public TeleportGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, Location location) {
        super(gui, item, cooldown, sound, permission);
        this.location = location;
    }

    @Override
    public void onClick(ClickData data) {
        Player player = data.getPlayer();
        player.closeInventory();
        player.teleport(location);
    }

    public static class Builder extends BaseGUIButton.Builder<Builder, TeleportGUIButton> {
        private Location location;

        public Builder(GUI gui) {
            super(gui);
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        @Override
        public TeleportGUIButton build() {
            return new TeleportGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.location);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
