package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class GUIItem extends GUISection {
    @Setter
    private ItemStack item;

    public GUIItem(GUI gui, ItemStack item) {
        super(gui, new Vector2d(1,1));
        this.item = item;
    }

    @Override
    public List<ItemStack> render() {
        return new ArrayList<>(List.of(item));
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
