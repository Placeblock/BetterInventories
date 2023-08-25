package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.builder.content.GUIItemBuilder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link GUISection} with a size of 1x1 containing an {@link ItemStack}
 * <p></p>
 * Builder: {@link GUIItemBuilder}
 */
@Getter
@SuppressWarnings("unused")
public class GUIItem extends GUISection {
    /**
     * The Size of every GUIItem
     */
    public static final Vector2d BUTTON_SIZE = new Vector2d(1, 1);
    /**
     * The ItemStack to be rendered
     */
    @Setter
    protected ItemStack itemStack;

    /**
     * Creates a new GUIItem
     * @param gui The GUI
     * @param itemStack The ItemStack. {@link ItemBuilder} may help to create it.
     */
    public GUIItem(GUI gui, ItemStack itemStack) {
        super(gui, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
        this.itemStack = itemStack;
    }

    /**
     * Renders the GUIItem on a list
     * @return The List
     */
    @Override
    public List<ItemStack> render() {
        return new ArrayList<>(List.of(itemStack));
    }

    /**
     * Returns the GUISection at a specific slot.
     * For GUIItems it will always return the GUIItem itself.
     * @param slot The slot
     * @return The GUISection
     */
    @Override
    public GUISection getSectionAt(int slot) {
        return this;
    }

    /**
     * Returns the GUISection at a specific position.
     * For GUIItems it will always return the GUIItem itself.
     * @param position The position
     * @return The GUISection
     */
    @Override
    public GUISection getSectionAt(Vector2d position) {
        return this;
    }
}
