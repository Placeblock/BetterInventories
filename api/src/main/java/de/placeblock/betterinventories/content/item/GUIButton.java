package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public abstract class GUIButton extends BaseGUIButton {
    /**
     * Creates a new GUIButton
     *
     * @param gui        The GUI
     * @param item       The ItemStack of the GUIButton
     * @param cooldown   The cooldown after which the button can be clicked again in ticks
     * @param clickSound The sound the button should make on click.
     *                   null if silent.
     * @param permission The permission required to click the button
     */
    @Deprecated(forRemoval = true)
    public GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound, String permission) {
        super(gui, item, cooldown, clickSound, permission);
    }

    public static class Builder extends BaseGUIButton.Builder<Builder, GUIButton> {

        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public GUIButton build() {
            return new GUIButton(this.getGui(), this.getItemStack(), this.getCooldown(), this.getSound(), this.getPermission()) {
                @Override
                public void onClick(ClickData data) {
                    if (Builder.this.getOnClick() != null) {
                        Builder.this.getOnClick().accept(data);
                    }
                }
                @Override
                public void onShiftClick(ClickData data) {
                    if (Builder.this.getOnShiftClick() != null) {
                        Builder.this.getOnShiftClick().accept(data);
                    }
                }
                @Override
                public void onRightClick(ClickData data) {
                    if (Builder.this.getOnRightClick() != null) {
                        Builder.this.getOnRightClick().accept(data);
                    }
                }
                @Override
                public void onLeftClick(ClickData data) {
                    if (Builder.this.getOnLeftClick() != null) {
                        Builder.this.getOnLeftClick().accept(data);
                    }
                }
                @Override
                public void onShiftRightClick(ClickData data) {
                    if (Builder.this.getOnShiftRightClick() != null) {
                        Builder.this.getOnShiftRightClick().accept(data);
                    }
                }
                @Override
                public void onShiftLeftClick(ClickData data) {
                    if (Builder.this.getOnShiftLeftClick() != null) {
                        Builder.this.getOnShiftLeftClick().accept(data);
                    }
                }

            };
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
