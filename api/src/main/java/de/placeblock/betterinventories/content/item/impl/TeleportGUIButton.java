package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link GUIButton} which automatically teleports the player to a specific {@link Location}
 */
@SuppressWarnings("unused")
public class TeleportGUIButton extends GUIButton {
    /**
     * The location to which the player is teleported
     */
    private final Location location;

    /**
     * Creates a new TeleportGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param location The location to which the player is teleported
     * @param cooldown The cooldown of the Button
     * @param permission The permission required to press this button
     * @param sound The sound played when pressing this button
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

    /**
     * Builder for creating {@link TeleportGUIButton}
     */
    public static class Builder extends AbstractBuilder<Builder, TeleportGUIButton> {
        private Location location;

        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        /**
         * Sets the location attribute
         * @param location The location to which the player is teleported
         * @return Itself
         */
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
