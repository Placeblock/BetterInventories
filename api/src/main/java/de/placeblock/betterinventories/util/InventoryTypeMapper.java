package de.placeblock.betterinventories.util;

import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.Map;

/**
 * Used internally to calculate the Inventory's size based on the InventoryType
 */
public class InventoryTypeMapper {

    /**
     * Contains all availible mappings
     */
    private static final Map<InventoryType, Vector2d> mappings = new HashMap<>();

    static {
        mappings.put(InventoryType.DISPENSER, new Vector2d(3, 3));
        mappings.put(InventoryType.DROPPER, new Vector2d(3, 3));
        mappings.put(InventoryType.ENDER_CHEST, new Vector2d(9, 3));
        mappings.put(InventoryType.BEACON, new Vector2d(1, 1));
        mappings.put(InventoryType.HOPPER, new Vector2d(5, 1));
        mappings.put(InventoryType.SHULKER_BOX, new Vector2d(9, 3));
        mappings.put(InventoryType.BARREL, new Vector2d(9, 3));
        mappings.put(InventoryType.LECTERN, new Vector2d(1, 1));
        mappings.put(InventoryType.CHEST, new Vector2d(9, 3));
    }

    /**
     * @param type The {@link InventoryType}
     * @return The size for this {@link InventoryType} or null if unsupported
     */
    public static Vector2d getSize(InventoryType type) {
        return mappings.get(type);
    }

}
