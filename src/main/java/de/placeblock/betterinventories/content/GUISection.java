package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A GUISection is the most basic element that can be put inside GUIs.
 */
@Getter
@SuppressWarnings("unused")
public abstract class GUISection implements Sizeable {
    private final GUI gui;
    private Vector2d size;
    protected Vector2d minSize;
    protected Vector2d maxSize;

    public GUISection(GUI gui, Vector2d size, Vector2d minSize, Vector2d maxSize) {
        this.gui = gui;
        this.size = size;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public void setSize(Vector2d vector2d) {
        this.size = vector2d;
    }

    public abstract List<ItemStack> render();

    public GUISection getSectionAt(int index) {
        return this.getSectionAt(this.slotToVector(index));
    }

    public abstract GUISection getSectionAt(Vector2d position);

    public Vector2d slotToVector(int index) {
        if (this.getSlots() == 0) return null;
        return Util.slotToVector(index, this.size.getX());
    }

    public <T> List<T> getEmptyContentList(Class<T> clazz) {
        return new ArrayList<>(Collections.nCopies(this.getSlots(), null));
    }

    public int vectorToSlot(Vector2d position) {
        return Util.vectorToSlot(position, this.size.getX());
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
}
