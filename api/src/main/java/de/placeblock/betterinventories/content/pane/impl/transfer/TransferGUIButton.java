package de.placeblock.betterinventories.content.pane.impl.transfer;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import org.bukkit.inventory.ItemStack;

public class TransferGUIButton extends GUIButton {
    private final TransferGUIPane pane;
    public TransferGUIButton(TransferGUIPane pane, ItemStack item) {
        super(pane.getGui(), item);
        this.pane = pane;
    }

    @Override
    public void onClick(ClickData data) {

    }
}
