package de.placeblock.betterinventories.interaction.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.interaction.InteractionHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * InteractionHandler for handling Button clicks
 */
public class ButtonClickHandler extends InteractionHandler {
    /**
     * The according GUI
     */
    protected final GUIButton button;

    /**
     * Creates a new ButtonClickHandler
     * @param button The according GUIButton
     */
    public ButtonClickHandler(GUIButton button) {
        this.button = button;
    }

    /**
     * Called on inventory-click
     * @param event The Event
     * @return true if handler-calling should stop
     */
    @Override
    public boolean onClick(InventoryClickEvent event) {
        ClickType clickType = event.getClick();
        boolean leftClick = clickType.isLeftClick();
        boolean rightClick = clickType.isRightClick();
        if (!leftClick && !rightClick) return false;

        int slot = event.getSlot();
        Player player = ((Player) event.getWhoClicked());
        ClickData clickData = new ClickData(player, slot, event.getAction(), event);
        button.click(player);
        if (event.isShiftClick()) {
            button.onShiftClick(clickData);
            if (leftClick) {
                button.onShiftLeftClick(clickData);
            } else {
                button.onShiftRightClick(clickData);
            }
        } else {
            button.onClick(clickData);
            if (leftClick) {
                button.onLeftClick(clickData);
            } else {
                button.onRightClick(clickData);
            }
        }
        return true;
    }

    /**
     * Called on inventory-drag
     * @param event The Event
     * @return true if handler-calling should stop
     */
    @Override
    public boolean onDrag(InventoryDragEvent event) {
        return false;
    }
}
