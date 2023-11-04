package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.BaseGUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
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
public abstract class ToggleGUIButton extends BaseGUIButton {
    /**
     * Whether the button is currently toggled
     */
    private boolean toggled;

    /**
     * Creates a new ToggleGUIButton
     * @param gui The GUI
     * @param permission The permission required to toggle
     * @param toggled The default value of the toggled-state
     */
    public ToggleGUIButton(GUI gui, int cooldown, Sound sound, String permission, boolean toggled) {
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
     * @param toggled The current state
     */
    protected abstract void onToggle(ClickData clickData, boolean toggled);



    public static class Builder extends BaseGUIButton.Builder<Builder, ToggleGUIButton> {
        private boolean toggled;
        private BiConsumer<ClickData, Boolean> onToggle;
        private Supplier<ItemStack> enabledItem;
        private Supplier<ItemStack> disabledItem;

        public Builder(GUI gui) {
            super(gui);
        }

        public Builder toggled(boolean toggled) {
            this.toggled = toggled;
            return this;
        }

        public Builder onToggle(BiConsumer<ClickData, Boolean> onToggle) {
            this.onToggle = onToggle;
            return this;
        }

        public Builder enabledItem(Supplier<ItemStack> enabledItem) {
            this.enabledItem = enabledItem;
            return this;
        }

        public Builder disabledItem(Supplier<ItemStack> disabledItem) {
            this.disabledItem = disabledItem;
            return this;
        }

        @Override
        public ToggleGUIButton build() {
            if (this.enabledItem == null || this.disabledItem == null) {
                throw new IllegalStateException("Enabled and Disabled items have to be set");
            }
            return new ToggleGUIButton(this.getGui(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.toggled) {
                @Override
                protected ItemStack getEnabledItem() {
                    return Builder.this.enabledItem.get();
                }

                @Override
                protected ItemStack getDisabledItem() {
                    return Builder.this.disabledItem.get();
                }

                @Override
                protected void onToggle(ClickData clickData, boolean toggled) {
                    Builder.this.onToggle.accept(clickData, toggled);
                }
            };
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
