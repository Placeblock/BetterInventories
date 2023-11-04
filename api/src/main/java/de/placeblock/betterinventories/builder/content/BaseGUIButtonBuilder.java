package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;

import java.util.function.Consumer;

/**
 * Base class for creating GUIButton-Builders
 * @param <G> The GUIButton type
 * @param <B> The Builder type
 */
@Deprecated(forRemoval = true)
@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseGUIButtonBuilder<G extends GUIButton, B extends BaseGUIButtonBuilder<G, B>> extends BaseGUIItemBuilder<G, B> {
    /**
     * The permission needed to click on the Button
     */
    private String permission = null;

    /**
     * The click-sound the Button makes on click
     */
    private Sound clickSound = null;

    /**
     * The cooldown the Button has until it can get clicked again.
     * @see GUIButton#setCooldown(int)
     */
    private int cooldown = 0;

    /**
     * Is called when the player clicks on the Button no matter how.
     */
    private Consumer<ClickData> onClick;

    /**
     * Is called when the player right-clicks the Button without sneaking
     */
    private Consumer<ClickData> onRightClick;

    /**
     * Is called when the player left-clicks the Button without sneaking
     */
    private Consumer<ClickData> onLeftClick;

    /**
     * Is called when the player clicks the Button while sneaking
     */
    private Consumer<ClickData> onShiftClick;

    /**
     * Is called when the player right-clicks the Button while sneaking
     */
    private Consumer<ClickData> onShiftRightClick;

    /**
     * Is called when the player left-clicks the Button while sneaking
     */
    private Consumer<ClickData> onShiftLeftClick;

    /**
     * Creates a new BaseGUIButtonBuilder
     * @param gui The GUI for the Button
     */
    public BaseGUIButtonBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Sets the onClick handler
     * @param onClick The handler
     * @return this
     */
    public B onClick(Consumer<ClickData> onClick) {
        this.onClick = onClick;
        return (B) this;
    }

    /**
     * Sets the onLeftClick handler
     * @param onLeftClick The handler
     * @return this
     */
    public B onLeftClick(Consumer<ClickData> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return (B) this;
    }

    /**
     * Sets the onRightClick handler
     * @param onRightClick The handler
     * @return this
     */
    public B onRightClick(Consumer<ClickData> onRightClick) {
        this.onRightClick = onRightClick;
        return (B) this;
    }

    /**
     * Sets the onShiftClick handler
     * @param onShiftClick The handler
     * @return this
     */
    public B onShiftClick(Consumer<ClickData> onShiftClick) {
        this.onShiftClick = onShiftClick;
        return (B) this;
    }

    /**
     * Sets the onShiftLeftClick handler
     * @param onShiftLeftClick The handler
     * @return this
     */
    public B onShiftLeftClick(Consumer<ClickData> onShiftLeftClick) {
        this.onShiftLeftClick = onShiftLeftClick;
        return (B) this;
    }

    /**
     * Sets the onShiftRightClick handler
     * @param onShiftRightClick The handler
     * @return this
     */
    public B onShiftRightClick(Consumer<ClickData> onShiftRightClick) {
        this.onShiftRightClick = onShiftRightClick;
        return (B) this;
    }

    /**
     * Sets the permission required to click on this button
     * @param permission The permission
     * @return this
     */
    public B permission(String permission) {
        this.permission = permission;
        return (B) this;
    }

    /**
     * Sets the click-sound this button makes on click
     * @param sound The sound
     * @return this
     */
    public B clickSound(Sound sound) {
        this.clickSound = sound;
        return (B) this;
    }

    /**
     * Sets the cooldown after which the button can get clicked again
     * @param cooldown The cooldown
     * @return this
     */
    public B cooldown(int cooldown) {
        this.cooldown = cooldown;
        return (B) this;
    }

    /**
     * @return The onClick Handler
     */
    protected Consumer<ClickData> getOnClick() {
        return this.onClick;
    }

    /**
     * @return The onLeftClick Handler
     */
    protected Consumer<ClickData> getOnLeftClick() {
        return this.onLeftClick;
    }

    /**
     * @return The onRightClick Handler
     */
    protected Consumer<ClickData> getOnRightClick() {
        return this.onRightClick;
    }

    /**
     * @return The onShiftClick Handler
     */
    protected Consumer<ClickData> getOnShiftClick() {
        return this.onShiftClick;
    }

    /**
     * @return The onShiftLeftClick Handler
     */
    protected Consumer<ClickData> getOnShiftLeftClick() {
        return this.onShiftLeftClick;
    }

    /**
     * @return The onShiftRightClick Handler
     */
    protected Consumer<ClickData> getOnShiftRightClick() {
        return this.onShiftRightClick;
    }

    /**
     * @return The cooldown after which the Button can be clicked again
     */
    protected int getCooldown() {return this.cooldown;}

    /**
     * @return The clickSound the Button makes when clicked or null
     */
    protected Sound getClickSound() {return this.clickSound;}

    /**
     * @return The permission required to click the Button or null
     */
    protected String getPermission() {return this.permission;}
}
