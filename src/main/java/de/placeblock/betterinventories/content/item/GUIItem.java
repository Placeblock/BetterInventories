package de.placeblock.betterinventories.content.item;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuppressWarnings("unused")
public class GUIItem extends GUISection {
    @Setter
    protected ItemStack itemStack;

    public GUIItem(GUI gui, ItemStack itemStack) {
        super(gui, new Vector2d(1,1), new Vector2d(1,1));
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
