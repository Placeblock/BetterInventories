package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.GUIView;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public abstract class GUIButton extends GUIItem {
    private final int cooldown;
    private final Sound clickSound;

    public GUIButton(GUI gui, ItemStack item) {
        this(gui, item, 0, Sound.UI_BUTTON_CLICK);
    }

    public GUIButton(GUI gui, ItemStack item, Sound clickSound) {
        this(gui, item, 0, clickSound);
    }

    public GUIButton(GUI gui, ItemStack item, int cooldown) {
        this(gui, item, cooldown, null);
    }

    public GUIButton(GUI gui, ItemStack item, int cooldown, Sound clickSound) {
        super(gui, item);
        this.cooldown = cooldown;
        this.clickSound = clickSound;
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
            view.getPlayer().setCooldown(this.item.getType(), cooldown);
        }
    }

    public abstract void onClick(Player player, int slot);
    public void onLeftClick(Player player, int slot) {}
    public void onRightClick(Player player, int slot) {}
    public void onShiftClick(Player player, int slot) {
        this.onClick(player, slot);
    }
    public void onShiftLeftClick(Player player, int slot) {
        this.onLeftClick(player, slot);
    }
    public void onShiftRightClick(Player player, int slot) {
        this.onRightClick(player, slot);
    }

}
