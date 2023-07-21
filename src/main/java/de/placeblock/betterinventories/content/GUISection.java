package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@SuppressWarnings("unused")
public abstract class GUISection {
    private final GUI gui;
    @Setter
    protected Vector2d size;

    public GUISection(GUI gui, Vector2d size) {
        this.gui = gui;
        this.size = size;
    }

    public abstract List<ItemStack> render();

    public GUISection getSectionAt(int index) {
        return this.getSectionAt(this.slotToVector(index));
    }

    public abstract GUISection getSectionAt(Vector2d position);

    public Vector2d slotToVector(int index) {
        return Util.slotToVector(index, this.size.getX());
    }

    public <T> List<T> getEmptyContentArray(Class<T> clazz) {
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

    public void setHeight(int height) {
        this.setSize(new Vector2d(this.getWidth(), height));
    }

    public void setWidth(int width) {
        this.setSize(new Vector2d(width, this.getHeight()));
    }
}
