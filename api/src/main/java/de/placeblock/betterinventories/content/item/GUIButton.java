package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.builder.content.GUIButtonBuilder;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.GUIView;
import de.placeblock.betterinventories.interaction.impl.ButtonClickHandler;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


/**
 * A {@link GUIItem} with the ability to get clicked.
 * Various onclick methods can be implemented and overridden.
 * The cooldown is being set for the whole material of the {@link ItemStack}.
 * <br>
 * Builder: {@link GUIButtonBuilder}
 */
@SuppressWarnings("unused")
public abstract class GUIButton extends GUIItem {
    /**
     * The cooldown of the Button before it can be clicked again in ticks.
     * The cooldown is being set for the whole material of the {@link ItemStack}.
     */
    private final int cooldown;
    /**
     * The sound the button should make on click.
     * null if silent.
     */
    private final Sound clickSound;
    /**
     * The permission the player needs to click the button.
     */
    private final String permission;

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     */
    public GUIButton(GUI gui, ItemStack item) {
        this(gui, item, 0, Sound.UI_BUTTON_CLICK);
    }

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param permission The permission required to click the button
     */
    public GUIButton(GUI gui, ItemStack item, String permission) {
        this(gui, item, 0, Sound.UI_BUTTON_CLICK);
    }

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param clickSound The sound the button should make on click.
     *                   null if silent.
     */
    public GUIButton(GUI gui, ItemStack item, Sound clickSound) {
        this(gui, item, 0, clickSound);
    }

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param cooldown The cooldown after which the button can be clicked again in ticks
     */
    public GUIButton(GUI gui, ItemStack item, int cooldown) {
        this(gui, item, cooldown, null, null);
    }

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param cooldown The cooldown after which the button can be clicked again in ticks
     * @param permission The permission required to click the button
     */
    public GUIButton(GUI gui, ItemStack item, int cooldown, String permission) {
        this(gui, item, cooldown, null, permission);
    }

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param cooldown The cooldown after which the button can be clicked again in ticks
     * @param clickSound The sound the button should make on click.
     *                   null if silent.
     */
    public GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound) {
        this(gui, item, cooldown, clickSound, null);
    }

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param cooldown The cooldown after which the button can be clicked again in ticks
     * @param clickSound The sound the button should make on click.
     *                   null if silent.
     * @param permission The permission required to click the button
     */
    public GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound, String permission) {
        super(gui, item);
        this.cooldown = cooldown;
        this.clickSound = clickSound;
        this.permission = permission;
        this.registerInteractionHandler(new ButtonClickHandler(this));
    }

    /**
     * Checks if the player has the permission to click this button
     * @param player The player
     * @return true if the player has the permission or the permission is null, false otherwise
     */
    public boolean hasPermission(Player player) {
        return this.permission == null || player.hasPermission(this.permission);
    }

    /**
     * Is called when a player clicks on the Button
     * @param player The Player
     */
    public void click(Player player) {
        if (this.cooldown > 0) {
            this.applyCooldown();
        }
        if (this.clickSound != null) {
            player.playSound(player.getEyeLocation(), this.clickSound, 1, 1);
        }
    }

    /**
     * Applies the cooldown to the Button
     */
    public void applyCooldown() {
        this.setCooldown(this.cooldown);
    }

    /**
     * Sets the cooldown after which the Button can be clicked again
     * @param cooldown The cooldown in ticks
     */
    public void setCooldown(int cooldown) {
        for (GUIView view : this.getGui().getViews()) {
            view.getPlayer().setCooldown(this.itemStack.getType(), cooldown);
        }
    }

    /**
     * Is called when the player clicks on the Button no matter how.
     * @param data The ClickData
     */
    public abstract void onClick(ClickData data);

    /**
     * Is called when the player left-clicks the Button without sneaking
     * @param data The ClickData
     */
    public void onLeftClick(ClickData data) {}

    /**
     * Is called when the player right-clicks the Button without sneaking
     * @param data The ClickData
     */
    public void onRightClick(ClickData data) {}

    /**
     * Is called when the player clicks the Button while sneaking
     * @param data The ClickData
     */
    public void onShiftClick(ClickData data) {
        this.onClick(data);
    }

    /**
     * Is called when the player left-clicks the Button while sneaking
     * @param data The ClickData
     */
    public void onShiftLeftClick(ClickData data) {
        this.onLeftClick(data);
    }

    /**
     * Is called when the player right-clicks the Button while sneaking
     * @param data The ClickData
     */
    public void onShiftRightClick(ClickData data) {
        this.onRightClick(data);
    }

}
