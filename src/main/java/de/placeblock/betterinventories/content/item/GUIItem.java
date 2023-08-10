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
    public static final Vector2d BUTTON_SIZE = new Vector2d(1, 1);
    @Setter
    protected ItemStack itemStack;

    /**
     * @param itemStack The ItemStack. {@link ItemBuilder} may help to create it.
     */
    public GUIItem(GUI gui, ItemStack itemStack) {
        super(gui, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
        this.itemStack = itemStack;
    }

    @Override
    public List<ItemStack> render() {
        return new ArrayList<>(List.of(itemStack));
    }

    @Override
    public GUISection getSectionAt(int index) {
        return this;
    }

    @Override
    public GUISection getSectionAt(Vector2d position) {
        return this;
    }
}
