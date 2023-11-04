package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.BaseGUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;


/**
 * A {@link BaseGUIButton} which executes a command.
 */
@SuppressWarnings("unused")
public abstract class BaseCommandGUIButton extends BaseGUIButton {
    /**
     * The command to be executed
     */
    private final String command;

    /**
     * Creates a new CommandGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param command The command to be executed
     */
    public BaseCommandGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, String command) {
        super(gui, item, cooldown, sound, permission);
        this.command = command;
    }

    @Override
    public void onClick(ClickData data) {
        data.getPlayer().performCommand(this.command);
    }

    @Getter(AccessLevel.PROTECTED)
    public static abstract class Builder<B extends Builder<B, P>, P extends BaseCommandGUIButton> extends BaseGUIButton.Builder<B, P> {
        private String command;

        public Builder(GUI gui) {
            super(gui);
        }

        public B command(String command) {
            this.command = command;
            return this.self();
        }
    }
}
