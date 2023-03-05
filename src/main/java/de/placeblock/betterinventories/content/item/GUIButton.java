package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.GUIView;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


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
