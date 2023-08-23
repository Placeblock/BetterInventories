package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.builder.content.GUIButtonBuilder;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.GUIView;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


/**
 * A {@link GUIItem} with the ability to get clicked.
 * Various onclick methods can be implemented and overridden.
 * The cooldown is being set for the whole material of the {@link ItemStack}.
 * <p></p>
 * Builder: {@link GUIButtonBuilder}
 */
@SuppressWarnings("unused")
public abstract class GUIButton extends GUIItem {
    private final int cooldown;
    private final Sound clickSound;
    private final String permission;

    public GUIButton(GUI gui, ItemStack item) {
        this(gui, item, 0, Sound.UI_BUTTON_CLICK);
    }

    public GUIButton(GUI gui, ItemStack item, String permission) {
        this(gui, item, 0, Sound.UI_BUTTON_CLICK);
    }

    public GUIButton(GUI gui, ItemStack item, Sound clickSound) {
        this(gui, item, 0, clickSound);
    }

    public GUIButton(GUI gui, ItemStack item, int cooldown) {
        this(gui, item, cooldown, null, null);
    }
    public GUIButton(GUI gui, ItemStack item, int cooldown, String permission) {
        this(gui, item, cooldown, null, permission);
    }
    public GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound) {
        this(gui, item, cooldown, clickSound, null);
    }

    public GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound, String permission) {
        super(gui, item);
        this.cooldown = cooldown;
        this.clickSound = clickSound;
        this.permission = permission;
    }

    public boolean hasPermission(Player player) {
        return this.permission == null || player.hasPermission(this.permission);
    }

    public void click(Player player) {
        if (this.cooldown > 0) {
            this.applyCooldown();
        }
        if (this.clickSound != null) {
            player.playSound(player.getEyeLocation(), this.clickSound, 1, 1);
        }
    }

    public void applyCooldown() {
        this.setCooldown(this.cooldown);
    }

    public void setCooldown(int cooldown) {
        for (GUIView view : this.getGui().getViews()) {
            view.getPlayer().setCooldown(this.itemStack.getType(), cooldown);
        }
    }

    public abstract void onClick(ClickData data);
    public void onLeftClick(ClickData data) {}
    public void onRightClick(ClickData data) {}
    public void onShiftClick(ClickData data) {
        this.onClick(data);
    }
    public void onShiftLeftClick(ClickData data) {
        this.onLeftClick(data);
    }
    public void onShiftRightClick(ClickData data) {
        this.onRightClick(data);
    }

}
