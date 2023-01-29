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
    protected Vector2d size;

    public abstract List<ItemStack> render();

    public GUISection getSectionAt(int index) {
        return this.getSectionAt(this.slotToVector(index));
    }

    public abstract GUISection getSectionAt(Vector2d position);

    public Vector2d slotToVector(int index) {
        return Util.slotToPosition(index, this.size.getX());
    }

    public <T> List<T> getEmptyContentArray(Class<T> clazz) {
        return new ArrayList<>(Collections.nCopies(this.getSlots(), null));
    }

    public int vectorToSlot(Vector2d position) {
        return position.getY()*this.size.getX()+position.getX();
    }

    public int getSlots() {
        return this.size.getX()*this.size.getY();
    }

    public int getHeight() {
        return this.size.getY();
    }

    public int getWidth() {
        return this.size.getX();
    }

    public void setHeight(int height) {
        this.size = new Vector2d(this.size.getX(), height);
    }

    public void setWidth(int width) {
        this.size = new Vector2d(width, this.size.getY());
    }
}
