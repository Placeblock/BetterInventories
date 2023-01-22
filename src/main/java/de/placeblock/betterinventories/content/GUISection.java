package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Placeblock
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("unused")
public abstract class GUISection {
    private final GUI gui;
    private int width;
    private int height;

    public abstract List<ItemStack> render();

    public GUISection getSectionAt(int index) {
        return this.getSectionAt(this.slotToVector(index));
    }

    public GUISection getSectionAt(int x, int y) {
        return this.getSectionAt(new Vector2d(x, y));
    }

    public abstract GUISection getSectionAt(Vector2d position);

    public Vector2d slotToVector(int index) {
        return Util.slotToPosition(index, this.width);
    }

    public <T> List<T> getEmptyContentArray(Class<T> clazz) {
        return new ArrayList<>(Collections.nCopies(this.getSlots(), null));
    }

    public int vectorToSlot(Vector2d position) {
        return position.getY()*this.width+position.getX();
    }

    public int getSlots() {
        return this.width*this.height;
    }

    public Vector2d getSize() {
        return new Vector2d(this.width, this.height);
    }
}
