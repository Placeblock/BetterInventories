package de.placeblock.betterinventories.content.pane.impl.io;

import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

/**
 * Used by {@link BaseIOGUIPane}
 */
@FunctionalInterface
public interface IOConsumer {
    /**
     * Executed when an item in the pane changes
     * @param position The position of the item
     * @param itemStack The current ItemStack after change
     */
    void apply(Vector2d position, ItemStack itemStack);

}
