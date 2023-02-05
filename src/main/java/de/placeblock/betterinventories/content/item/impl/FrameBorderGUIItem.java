package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;


public abstract class FrameBorderGUIItem extends GUIButton {
    public static final int MAX_CLICK_DELAY = 400;

    // FOR EASTER EGG
    private long lastClick = 0;
    private int clicks = 0;

    public FrameBorderGUIItem(GUI gui) {
        super(gui, GUI.PLACEHOLDER_ITEM, null);
    }

    @Override
    public void onClick(Player player, int slot) {
        long time = System.currentTimeMillis();
        if (time- this.lastClick > MAX_CLICK_DELAY) {
            this.clicks = 0;
        } else {
            this.clicks++;
            if (this.clicks > 3) {
                this.onActivate(slot);
                this.clicks = 0;
            }
        }
        this.lastClick = time;
    }

    protected abstract void onActivate(int slot);
}
