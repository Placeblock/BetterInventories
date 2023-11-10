package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;


/**
 * A {@link GUIButton} which executes a command.
 */
@SuppressWarnings("unused")
public class CommandGUIButton extends GUIButton {
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
    public CommandGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, String command) {
        super(gui, item, cooldown, sound, permission);
        this.command = command;
    }

    @Override
    public void onClick(ClickData data) {
        data.getPlayer().performCommand(this.command);
    }


    @Getter(AccessLevel.PROTECTED)
    protected static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends CommandGUIButton> extends GUIButton.AbstractBuilder<B, P> {
        private String command;

        public AbstractBuilder(GUI gui) {
            super(gui);
        }

        public B command(String command) {
            this.command = command;
            return this.self();
        }
    }

    public static class Builder extends AbstractBuilder<Builder, CommandGUIButton> {
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
