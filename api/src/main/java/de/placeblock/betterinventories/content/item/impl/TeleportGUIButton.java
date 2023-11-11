package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
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
    protected TeleportGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, Location location) {
        super(gui, item, cooldown, sound, permission);
        this.location = location;
    }

    @Override
    public void onClick(ClickData data) {
        Player player = data.player();
        player.closeInventory();
        player.teleport(location);
    }

    /**
     * Abstract Builder for creating {@link TeleportGUIButton}
     * @param <B> The Builder that implements this one
     * @param <P> The {@link CommandGUIButton} that is build
     */
    @Getter(AccessLevel.PROTECTED)
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends TeleportGUIButton> extends GUIButton.AbstractBuilder<B, P> {
        private Location location;

        /**
         * Sets the location attribute
         * @param location The location to which the player is teleported
         * @return Itself
         */
        public B location(Location location) {
            this.location = location;
            return this.self();
        }

        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        protected AbstractBuilder(GUI gui) {
            super(gui);
        }
    }

    /**
     * Builder for creating {@link TeleportGUIButton}
     */
    public static class Builder extends AbstractBuilder<Builder, TeleportGUIButton> {

        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public TeleportGUIButton build() {
            return new TeleportGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.getLocation());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
