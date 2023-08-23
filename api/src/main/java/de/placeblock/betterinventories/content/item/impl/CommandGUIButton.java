package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;


/**
 * A {@link GUIButton} which executes a command.
 */
@SuppressWarnings("unused")
public class CommandGUIButton extends GUIButton {
    private final String command;

    public CommandGUIButton(GUI gui, ItemStack item, String command) {
        super(gui, item);
        this.command = command;
    }

    @Override
    public void onClick(ClickData data) {
        data.getPlayer().performCommand(this.command);
    }
}
