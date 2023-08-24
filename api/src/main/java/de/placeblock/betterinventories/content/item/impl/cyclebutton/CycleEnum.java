package de.placeblock.betterinventories.content.item.impl.cyclebutton;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;

import java.util.List;

/**
 * Interface used by the {@link CycleGUIButton}
 * Has to be implemented by the Enum to provide needed data to {@link CycleGUIButton}
 */
public interface CycleEnum {

    /**
     * The Material of the ItemStack for the Enum-value
     * @return The Material
     */
    Material getMaterial();

    /**
     * The title of the ItemStack for the Enum-value
     * @return The title
     */
    TextComponent getTitle();

    /**
     * The lore of the ItemStack for the Enum-value
     * @return The lore
     */
    List<TextComponent> getLore();

}
