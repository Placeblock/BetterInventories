package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.GUIView;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;


/**
 * A {@link GUIItem} with the ability to get clicked.
 * Various onclick methods can be implemented and overridden.
 * The cooldown is being set for the whole material of the {@link ItemStack}.
 */
@SuppressWarnings("unused")
public class GUIButton extends GUIItem {
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
     * Called when the player clicks a button
     */
    private final Consumer<ClickData> onClick;
    /**
     * Called when the player shift-clicks a button
     */
    private final Consumer<ClickData> onShiftClick;
    /**
     * Called when the player left-clicks a button
     */
    private final Consumer<ClickData> onLeftClick;
    /**
     * Called when the player right-clicks a button
     */
    private final Consumer<ClickData> onRightClick;
    /**
     * Called when the player shift-right-clicks a button
     */
    private final Consumer<ClickData> onShiftRightClick;
    /**
     * Called when the player shift-left-clicks a button
     */
    private final Consumer<ClickData> onShiftLeftClick;

    /**
     * Creates a new GUIButton
     * @param gui The GUI
     * @param item The ItemStack of the GUIButton
     * @param cooldown The cooldown after which the button can be clicked again in ticks
     * @param clickSound The sound the button should make on click.
     *                   null if silent.
     * @param permission The permission required to click the button
     * @param onClick Called when the player clicks a button
     * @param onShiftClick Called when the player shift-clicks a button
     * @param onLeftClick Called when the player left-clicks a button
     * @param onRightClick Called when the player right-clicks a button
     * @param onShiftLeftClick Called when the player shift-right-clicks a button
     * @param onShiftRightClick Called when the player shift-left-clicks a button
     */
    protected GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound, String permission,
                        Consumer<ClickData> onClick, Consumer<ClickData> onShiftClick, Consumer<ClickData> onLeftClick,
                        Consumer<ClickData> onRightClick, Consumer<ClickData> onShiftLeftClick,Consumer<ClickData> onShiftRightClick) {
        super(gui, item);
        this.cooldown = cooldown;
        this.clickSound = clickSound;
        this.permission = permission;
        this.onClick = onClick;
        this.onShiftClick = onShiftClick;
        this.onLeftClick = onLeftClick;
        this.onRightClick = onRightClick;
        this.onShiftRightClick = onShiftRightClick;
        this.onShiftLeftClick = onShiftLeftClick;
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
    protected GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound, String permission) {
        this(gui, item, cooldown, clickSound, permission, null, null, null, null, null, null);
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
            player.playSound(player.getEyeLocation(), this.clickSound, 0.8F, 1);
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
            view.player().setCooldown(this.itemStack.getType(), cooldown);
        }
    }

    /**
     * Is called when the player clicks on the Button no matter how.
     * @param data The ClickData
     */
    public void onClick(ClickData data) {
        if (this.onClick != null) {
            this.onClick.accept(data);
        }
    }

    /**
     * Is called when the player left-clicks the Button without sneaking
     * @param data The ClickData
     */
    public void onLeftClick(ClickData data) {
        if (this.onLeftClick != null) {
            this.onLeftClick.accept(data);
        }}

    /**
     * Is called when the player right-clicks the Button without sneaking
     * @param data The ClickData
     */
    public void onRightClick(ClickData data) {
        if (this.onRightClick != null) {
            this.onRightClick.accept(data);
        }}

    /**
     * Is called when the player clicks the Button while sneaking
     * @param data The ClickData
     */
    public void onShiftClick(ClickData data) {
        if (this.onShiftClick != null) {
            this.onShiftClick.accept(data);
        }
    }

    /**
     * Is called when the player left-clicks the Button while sneaking
     * @param data The ClickData
     */
    public void onShiftLeftClick(ClickData data) {
        if (this.onShiftLeftClick != null) {
            this.onShiftLeftClick.accept(data);
        }
    }

    /**
     * Is called when the player right-clicks the Button while sneaking
     * @param data The ClickData
     */
    public void onShiftRightClick(ClickData data) {
        if (this.onShiftRightClick != null) {
            this.onShiftRightClick.accept(data);
        }
    }

    @Override
    public void onItemClick(ClickData data) {
        InventoryClickEvent event = data.event();
        boolean leftClick = event.isLeftClick();
        boolean rightClick = event.isRightClick();
        boolean shiftClick = event.isShiftClick();
        this.click(data.player());
        this.onClick(data);
        if (leftClick) {
            if (shiftClick) {
                this.onShiftLeftClick(data);
            } else {
                this.onLeftClick(data);
            }
        } else if (rightClick) {
            if (shiftClick) {
                this.onShiftRightClick(data);
            } else {
                this.onRightClick(data);
            }
        }
    }

    /**
     * Abstract Builder for creating various {@link GUIButton}
     * @param <B> The Builder that implements this one
     * @param <P> The {@link GUIButton} that is built
     */
    @Getter(AccessLevel.PROTECTED)
    protected static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends GUIButton> extends GUIItem.AbstractBuilder<B, P> {
        private String permission;
        private int cooldown = 0;
        private Sound sound = Sound.UI_BUTTON_CLICK;
        private Consumer<ClickData> onClick;
        private Consumer<ClickData> onShiftClick;
        private Consumer<ClickData> onLeftClick;
        private Consumer<ClickData> onRightClick;
        private Consumer<ClickData> onShiftRightClick;
        private Consumer<ClickData> onShiftLeftClick;

        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        protected AbstractBuilder(GUI gui) {
            super(gui);
        }

        /**
         * Sets the permission attribute
         * @param permission The permission that is required to click on the button
         * @return Itself
         */
        public B permission(String permission) {
            this.permission = permission;
            return this.self();
        }

        /**
         * Sets the cooldown attribute
         * @param cooldown The cooldown after which the button can be pressed again
         * @return Itself
         */
        public B cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this.self();
        }

        /**
         * Sets the sound attribute
         * @param sound The sound which is played when the button is pressed
         * @return Itself
         */
        public B sound(Sound sound) {
            this.sound = sound;
            return this.self();
        }

        /**
         * Sets the onClick attribute
         * @param callback Gets executed when the button is clicked
         * @return Itself
         */
        public B onClick(Consumer<ClickData> callback) {
            this.onClick = callback;
            return this.self();
        }

        /**
         * Sets the onShiftClick attribute
         * @param onShiftClick Gets executed when the button is shift-clicked
         * @return Itself
         */
        public B onShiftClick(Consumer<ClickData> onShiftClick) {
            this.onShiftClick = onShiftClick;
            return this.self();
        }

        /**
         * Sets the onShiftClick attribute
         * @param onRightClick Gets executed when the button is right-clicked
         * @return Itself
         */
        public B onRightClick(Consumer<ClickData> onRightClick) {
            this.onRightClick = onRightClick;
            return this.self();
        }

        /**
         * Sets the onLeftClick attribute
         * @param onLeftClick Gets executed when the button is left-clicked
         * @return Itself
         */
        public B onLeftClick(Consumer<ClickData> onLeftClick) {
            this.onLeftClick = onLeftClick;
            return this.self();
        }

        /**
         * Sets the onShiftRightClick attribute
         * @param onShiftRightClick Gets executed when the button is shift-right-clicked
         * @return Itself
         */
        public B onShiftRightClick(Consumer<ClickData> onShiftRightClick) {
            this.onShiftRightClick = onShiftRightClick;
            return this.self();
        }

        /**
         * Sets the onShiftLeftClick attribute
         * @param onShiftLeftClick Gets executed when the button is shift-left-clicked
         * @return Itself
         */
        public B onShiftLeftClick(Consumer<ClickData> onShiftLeftClick) {
            this.onShiftLeftClick = onShiftLeftClick;
            return this.self();
        }
    }

    /**
     * Builder for creating {@link GUIButton}
     */
    public static class Builder extends AbstractBuilder<Builder, GUIButton> {
        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public GUIButton build() {
            return new GUIButton(this.getGui(), this.getItemStack(), this.getCooldown(), this.getSound(), this.getPermission(),
                    this.getOnClick(), this.getOnShiftClick(), this.getOnLeftClick(), this.getOnRightClick(),
                    this.getOnShiftLeftClick(), this.getOnShiftRightClick());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
