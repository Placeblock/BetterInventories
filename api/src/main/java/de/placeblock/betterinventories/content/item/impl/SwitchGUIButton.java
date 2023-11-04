package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class SwitchGUIButton extends BaseSwitchGUIButton {
    /**
     * Creates a new SwitchGUIButton
     * The creation of the targetGUI can use the player which clicked the button
     *
     * @param gui       The GUI
     * @param item      The ItemStack of the Button
     * @param targetGUI The GUI to be opened
     */
    @Deprecated(forRemoval = true)
    public SwitchGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, Function<Player, GUI> targetGUI) {
        super(gui, item, cooldown, sound, permission, targetGUI);
    }

    public static class Builder extends BaseSwitchGUIButton.Builder<Builder, SwitchGUIButton> {
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public SwitchGUIButton build() {
            return new SwitchGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.getTargetGUI());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
