package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.content.pane.size.PanePos;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Getter
@SuppressWarnings("unused")
public abstract class GUISection {
    private final GUI gui;
    protected Vector2d absoluteSize;
    protected Vector2d minSize;
    protected Vector2d maxSize;
    protected PanePos size;

    public GUISection(GUI gui, Vector2d minSize, Vector2d maxSize, PanePos size) {
        this.gui = gui;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.size = size;
    }

    public abstract List<ItemStack> render();

    public abstract void updateSize(GUISection parent);

    public abstract Collection<GUISection> getChildren();

    public GUISection getSectionAt(int index) {
        return this.getSectionAt(this.slotToVector(index));
    }

    public abstract GUISection getSectionAt(Vector2d position);

    public Vector2d slotToVector(int index) {
        return Util.slotToVector(index, this.absoluteSize.getX());
    }

    public <T> List<T> getEmptyContentArray(Class<T> clazz) {
        return new ArrayList<>(Collections.nCopies(this.getSlots(), null));
    }

    public int vectorToSlot(Vector2d position) {
        return Util.vectorToSlot(position, this.absoluteSize.getX());
    }

    public int getSlots() {
        return this.absoluteSize.getX()*this.absoluteSize.getY();
    }

    public int getHeight() {
        return this.absoluteSize.getY();
    }

    public int getWidth() {
        return this.absoluteSize.getX();
    }
}
