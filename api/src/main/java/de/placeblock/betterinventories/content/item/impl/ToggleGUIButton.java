package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

/**
 * GUIButton which toggles between on and off
 */
@SuppressWarnings("unused")
@Getter
public abstract class ToggleGUIButton extends GUIButton {
    /**
     * Whether the button is currently toggled
     */
    private boolean toggled;

    /**
     * Creates a new ToggleGUIButton
     * @param gui The GUI
     */
    public ToggleGUIButton(GUI gui) {
        this(gui, null);
    }

    /**
     * Creates a new ToggleGUIButton
     * @param gui The GUI
     * @param toggled The default value of the toggled-state
     */
    public ToggleGUIButton(GUI gui, boolean toggled) {
        this(gui, null, toggled);
    }

    /**
     * Creates a new ToggleGUIButton
     * @param gui The GUI
     * @param permission The permission required to toggle
     */
    public ToggleGUIButton(GUI gui, String permission) {
        this(gui, permission, false);
    }

    /**
     * Creates a new ToggleGUIButton
     * @param gui The GUI
     * @param permission The permission required to toggle
     * @param toggled The default value of the toggled-state
     */
    public ToggleGUIButton(GUI gui, String permission, boolean toggled) {
        super(gui, null, permission);
        this.toggled = toggled;
        this.updateItem();
    }

    /**
     * Toggles the state of the Button
     */
    public void toggle() {
        this.toggled = !this.toggled;
        this.onToggle(this.toggled);
        this.updateItem();
    }

    /**
     * Updates the Item of the Button using the current toggled state
     */
    private void updateItem() {
        this.setItemStack(this.toggled ? this.getEnabledItem() : this.getDisabledItem());
    }

    /**
     * @return The Item for the enabled-state
     */
    protected abstract ItemStack getEnabledItem();

    /**
     * @return The Item for the disabled-state
     */
    protected abstract ItemStack getDisabledItem();

    /**
     * Gets called when the Button gets toggled
     * @param toggled The current state
     */
    protected abstract void onToggle(boolean toggled);
}
