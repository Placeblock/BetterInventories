package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class CommandGUIButton extends BaseCommandGUIButton {
    /**
     * Creates a new CommandGUIButton
     *
     * @param gui     The GUI
     * @param item    The ItemStack of the Button
     * @param command The command to be executed
     */
    public CommandGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, String command) {
        super(gui, item, cooldown, sound, permission, command);
    }

    public static class Builder extends BaseCommandGUIButton.Builder<Builder, CommandGUIButton> {
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public CommandGUIButton build() {
            return new CommandGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.getCommand());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
