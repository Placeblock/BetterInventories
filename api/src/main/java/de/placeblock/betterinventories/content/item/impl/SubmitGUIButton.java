package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Button which shows a confirm-item if clicked.
 */
@SuppressWarnings("unused")
public abstract class SubmitGUIButton extends GUIButton {
    /**
     * The default submit-item
     */
    public static final ItemStack SUBMIT_ITEM = new ItemBuilder(Component.text("Bestätigen"), Material.GREEN_DYE).build();
    /**
     * The item to be displayed on submit
     */
    private final ItemStack submitItem;
    /**
     * The item to be displayed by default
     */
    private final ItemStack item;
    /**
     * true if the player is in the submit-phase, which means
     * he confirms by clicking again
     */
    private boolean submitPhase = false;
    /**
     * true if the timer is running after which the submit-phase starts
     */
    private boolean delayTimerRunning = false;
    /**
     *  The delay before the submit item is shown
     */
    private final int submitDelay;

    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param submitItem The item to be displayed on submit
     * @param permission The permission required to click on this button
     * @param submitDelay The delay before the submit item is shown
     */
    public SubmitGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, ItemStack submitItem, int submitDelay) {
        super(gui, item, cooldown, sound, permission);
        this.item = item;
        this.submitItem = submitItem == null ? SUBMIT_ITEM : submitItem;
        this.submitDelay = submitDelay;
    }

    @Override
    public void onClick(ClickData data) {
        if (this.submitPhase) {
            this.onSubmit(data);
        } else if (!this.delayTimerRunning) {
            if (this.submitDelay == 0)  {
                this.startSubmitPhase();
            } else {
                this.delayTimerRunning = true;
                Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), this::startSubmitPhase,this.submitDelay);
            }
        }
    }

    /**
     * Starts the submit-phase
     * Starts timer for ending the submit-phase
     */
    private void startSubmitPhase() {
        this.setItemStack(this.submitItem);
        this.delayTimerRunning = false;
        this.submitPhase = true;
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), this::endSubmitPhase,100L);
    }

    /**
     * Ends the submit-phase
     */
    private void endSubmitPhase() {
        this.setItemStack(this.item);
        this.submitPhase = false;
    }

    /**
     * Is called on submit
     * @param data The ClickData of the click
     */
    public abstract void onSubmit(ClickData data);

    @Getter(AccessLevel.PROTECTED)
    protected static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends SubmitGUIButton> extends GUIButton.AbstractBuilder<B, P> {
        private ItemStack submitItem = new ItemBuilder(Component.text("Submit"), Material.LIME_DYE).build();
        private String permission;
        private int submitDelay;
        private Consumer<ClickData> onSubmit;

        public AbstractBuilder(GUI gui) {
            super(gui);
        }

        public B submitItem(ItemStack item) {
            this.submitItem = item;
            return this.self();
        }

        public B permission(String permission) {
            this.permission = permission;
            return this.self();
        }

        public B submitDelay(int submitDelay) {
            this.submitDelay = submitDelay;
            return this.self();
        }

        public B onSubmit(Consumer<ClickData> onSubmit) {
            this.onSubmit = onSubmit;
            return this.self();
        }
    }

    public static class Builder extends AbstractBuilder<Builder, SubmitGUIButton> {
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
