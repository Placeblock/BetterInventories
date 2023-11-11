package de.placeblock.betterinventories.content.item.impl.togglebutton;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

/**
 * GUIButton which toggles between on and off
 */
@SuppressWarnings("unused")
@Getter
public class ToggleGUIButton extends GUIButton {
    /**
     * Whether the button is currently toggled
     */
    private boolean toggled;
    /**
     * Used to get the item which is displayed when the button is enabled
     */
    private final Supplier<ItemStack> enabledItem;
    /**
     * Used to get the item which is displayed when the button is disabled
     */
    private final Supplier<ItemStack> disabledItem;
    /**
     * Executed when the button is toggled
     */
    private final ToggleConsumer onToggle;

    /**
     * Creates a new ToggleGUIButton
     * @param gui The GUI
     * @param permission The permission required to toggle
     * @param toggled The default value of the toggled-state
     * @param cooldown The cooldown of the Button
     * @param sound The sound played when pressing this button
     * @param enabledItem Used to get the item which is displayed when the button is enabled
     * @param disabledItem Used to get the item which is displayed when the button is disabled
     * @param onToggle Executed when the button is toggled
     */
    protected ToggleGUIButton(GUI gui, int cooldown, Sound sound, String permission, boolean toggled,
                              Supplier<ItemStack> enabledItem, Supplier<ItemStack> disabledItem, ToggleConsumer onToggle) {
        super(gui, null, cooldown, sound, permission);
        this.toggled = toggled;
        this.enabledItem = enabledItem;
        this.disabledItem = disabledItem;
        this.onToggle = onToggle;
        this.updateItem();
    }

    /**
     * Is called when the player clicks on the Button no matter how.
     * @param data The ClickData
     */
    @Override
    public void onClick(ClickData data) {
        this.toggle(data);
    }

    /**
     * Toggles the state of the Button
     * @param clickData The clickData of the action
     */
    public void toggle(ClickData clickData) {
        this.toggled = !this.toggled;
        this.onToggle(clickData, this.toggled);
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
    protected ItemStack getEnabledItem() {
        if (this.enabledItem == null) throw new IllegalStateException("ToggleGUIButton without enabled item supplier");
        return this.enabledItem.get();
    }

    /**
     * @return The Item for the disabled-state
     */
    protected ItemStack getDisabledItem() {
        if (this.disabledItem == null) throw new IllegalStateException("ToggleGUIButton without disabled item supplier");
        return this.disabledItem.get();
    }

    /**
     * Gets called when the Button gets toggled
     * @param clickData The clickData of the action
     * @param toggled The current state
     */
    protected void onToggle(ClickData clickData, boolean toggled) {
        if (this.onToggle != null) {
            this.onToggle.apply(clickData, toggled);
        }
    }


    /**
     * Abstract Builder for creating {@link ToggleGUIButton}
     */
    @Getter(AccessLevel.PROTECTED)
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends ToggleGUIButton> extends GUIButton.AbstractBuilder<B, P> {
        private boolean toggled;
        private ToggleConsumer onToggle;
        private Supplier<ItemStack> enabledItem;
        private Supplier<ItemStack> disabledItem;

        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        protected AbstractBuilder(GUI gui) {
            super(gui);
        }

        /**
         * Sets the toggled attribute
         * @param toggled Default value for the toggled state
         * @return Itself
         */
        public B toggled(boolean toggled) {
            this.toggled = toggled;
            return this.self();
        }

        /**
         * Sets the onToggle attribute
         * @param onToggle Is called if the button is toggled
         * @return Itself
         */
        public B onToggle(ToggleConsumer onToggle) {
            this.onToggle = onToggle;
            return this.self();
        }

        /**
         * Sets the enabledItem attribute
         * @param enabledItem This item is shown if the button is toggled
         * @return Itself
         */
        public B enabledItem(Supplier<ItemStack> enabledItem) {
            this.enabledItem = enabledItem;
            return this.self();
        }

        /**
         * Sets the disabledItem attribute
         * @param disabledItem This item is shown if the button is not toggled
         * @return Itself
         */
        public B disabledItem(Supplier<ItemStack> disabledItem) {
            this.disabledItem = disabledItem;
            return this.self();
        }
    }

    /**
     * Builder for creating {@link ToggleGUIButton}
     */
    public static class Builder extends AbstractBuilder<Builder, ToggleGUIButton> {

        /**
         * Creates a new Builder
         *
         * @param gui The gui this button belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public ToggleGUIButton build() {
            if (this.getEnabledItem() == null || this.getDisabledItem() == null) {
                throw new IllegalStateException("Enabled and Disabled items have to be set");
            }
            return new ToggleGUIButton(this.getGui(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.isToggled(), this.getEnabledItem(),
                    this.getDisabledItem(), this.getOnToggle());
        }

        @Override
        protected Builder self() {
            return this;
        }

    }

}
