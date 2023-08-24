package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.builder.content.SubmitGUIButtonBuilder;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Button which shows a confirm-item if clicked.
 * Builder: {@link SubmitGUIButtonBuilder}
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
     */
    public SubmitGUIButton(GUI gui, ItemStack item) {
        this(gui, item, null, null);
    }
    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param submitDelay The delay before the submit item is shown
     */
    public SubmitGUIButton(GUI gui, ItemStack item, int submitDelay) {
        this(gui, item, null, null, submitDelay);
    }
    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param submitItem The item to be displayed on submit
     */
    public SubmitGUIButton(GUI gui, ItemStack item, ItemStack submitItem) {
        this(gui, item, submitItem, null);
    }
    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param submitDelay The delay before the submit item is shown
     * @param submitItem The item to be displayed on submit
     */
    public SubmitGUIButton(GUI gui, ItemStack item, ItemStack submitItem, int submitDelay) {
        this(gui, item, submitItem, null, submitDelay);
    }
    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param permission The permission required to click on this button
     */
    public SubmitGUIButton(GUI gui, ItemStack item, String permission) {
        this(gui, item, null, permission);
    }
    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param permission The permission required to click on this buttonon
     * @param submitDelay The delay before the submit item is shown
     */
    public SubmitGUIButton(GUI gui, ItemStack item, String permission, int submitDelay) {
        this(gui, item, null, permission, submitDelay);
    }
    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param submitItem The item to be displayed on submit
     * @param permission The permission required to click on this button
     */
    public SubmitGUIButton(GUI gui, ItemStack item, ItemStack submitItem, String permission) {
        this(gui, item, submitItem, permission, 0);
    }
    /**
     * Creates a new SubmitGUIButton
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param submitItem The item to be displayed on submit
     * @param permission The permission required to click on this button
     * @param submitDelay The delay before the submit item is shown
     */
    public SubmitGUIButton(GUI gui, ItemStack item, ItemStack submitItem, String permission, int submitDelay) {
        super(gui, item, permission);
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
     */
    public abstract void onSubmit(ClickData data);
}
