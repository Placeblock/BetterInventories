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

    @Override
    public void onItemClick(ClickData data) {
        InventoryClickEvent event = data.getEvent();
        boolean leftClick = event.isLeftClick();
        boolean rightClick = event.isRightClick();
        boolean shiftClick = event.isShiftClick();
        this.click(data.getPlayer());
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

        public AbstractBuilder(GUI gui) {
            super(gui);
        }

        public B permission(String permission) {
            this.permission = permission;
            return this.self();
        }

        public B cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this.self();
        }

        public B sound(Sound sound) {
            this.sound = sound;
            return this.self();
        }

        public B onClick(Consumer<ClickData> callback) {
            this.onClick = callback;
            return this.self();
        }

        public B onShiftClick(Consumer<ClickData> onShiftClick) {
            this.onShiftClick = onShiftClick;
            return this.self();
        }

        public B onRightClick(Consumer<ClickData> onRightClick) {
            this.onRightClick = onRightClick;
            return this.self();
        }

        public B onLeftClick(Consumer<ClickData> onLeftClick) {
            this.onLeftClick = onLeftClick;
            return this.self();
        }

        public B onShiftRightClick(Consumer<ClickData> onShiftRightClick) {
            this.onShiftRightClick = onShiftRightClick;
            return this.self();
        }

        public B onShiftLeftClick(Consumer<ClickData> onShiftLeftClick) {
            this.onShiftLeftClick = onShiftLeftClick;
            return this.self();
        }
    }

    public static class Builder extends AbstractBuilder<Builder, GUIButton> {

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
