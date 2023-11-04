package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public abstract class SubmitGUIButton extends BaseSubmitGUIButton {
    /**
     * Creates a new SubmitGUIButton
     *
     * @param gui         The GUI
     * @param item        The ItemStack of the Button
     * @param submitItem  The item to be displayed on submit
     * @param submitDelay The delay before the submit item is shown
     */
    public SubmitGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, ItemStack submitItem, int submitDelay) {
        super(gui, item, cooldown, sound, permission, submitItem, submitDelay);
    }

    public static class Builder extends BaseSubmitGUIButton.Builder<Builder, SubmitGUIButton> {
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public SubmitGUIButton build() {
            return new SubmitGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(), this.getSound(),
                    this.getPermission(), this.getSubmitItem(), this.getSubmitDelay()) {
                @Override
                public void onSubmit(ClickData data) {
                    Consumer<ClickData> onSubmit = Builder.this.getOnSubmit();
                    if (onSubmit != null) {
                        onSubmit.accept(data);
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
