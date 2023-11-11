package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

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
     * @param permission The permission required to toggle
     * @param toggled The default value of the toggled-state
     * @param cooldown The cooldown of the Button
     * @param sound The sound played when pressing this button
     */
    protected ToggleGUIButton(GUI gui, int cooldown, Sound sound, String permission, boolean toggled) {
        super(gui, null, cooldown, sound, permission);
        this.toggled = toggled;
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
    protected abstract ItemStack getEnabledItem();

    /**
     * @return The Item for the disabled-state
     */
    protected abstract ItemStack getDisabledItem();

    /**
     * Gets called when the Button gets toggled
     * @param clickData The clickData of the action
     * @param toggled The current state
     */
    protected abstract void onToggle(ClickData clickData, boolean toggled);


    /**
     * Abstract Builder for creating {@link ToggleGUIButton}
     */
    @Getter(AccessLevel.PROTECTED)
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends ToggleGUIButton> extends GUIButton.AbstractBuilder<B, P> {
        private boolean toggled;
        private BiConsumer<ClickData, Boolean> onToggle;
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
        public B onToggle(BiConsumer<ClickData, Boolean> onToggle) {
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
                    this.getSound(), this.getPermission(), this.isToggled()) {
                @Override
                protected ItemStack getEnabledItem() {
                    return Builder.this.getEnabledItem().get();
                }

                @Override
                protected ItemStack getDisabledItem() {
                    return Builder.this.getDisabledItem().get();
                }

                @Override
                protected void onToggle(ClickData clickData, boolean toggled) {
                    Builder.this.getOnToggle().accept(clickData, toggled);
                }
            };
        }

        @Override
        protected Builder self() {
            return this;
        }

    }

}
