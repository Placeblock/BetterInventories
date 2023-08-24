package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * The Base for {@link GUIPane}s where content can be just placed with a {@link Vector2d} as the position.
 * @param <C> Which types of {@link GUISection} can be placed inside the BaseSimpleGUIPane
 * @param <S> The implementing class to return this-type correctly e.g. {@link #addItemEmptySlot(GUIItem)}
 */
@SuppressWarnings("unchecked")
public class BaseSimpleGUIPane<C extends GUISection, S extends BaseSimpleGUIPane<C, S>> extends GUIPane {
    private final Map<Vector2d, C> content = new HashMap<>();
    private final boolean autoSize;

    /**
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public BaseSimpleGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize);
        this.autoSize = autoSize;
    }

    @Override
    public void updateSizeRecursive(GUIPane parent) {
        this.updateChildrenRecursive(parent);
        this.updateSize(parent);
    }

    @Override
    public void updateSize(GUIPane parent) {
        if (this.autoSize) {
            Set<Vector2d> vectors = new HashSet<>();
            for (Map.Entry<Vector2d, C> childEntry : this.content.entrySet()) {
                vectors.add(childEntry.getKey().add(childEntry.getValue().getSize()));
            }
            this.setSize(Vector2d.largest(vectors));
        }
    }

    @Override
    public Set<GUISection> getChildren() {
        return new HashSet<>(this.content.values());
    }

    @Override
    public List<ItemStack> render() {
        List<ItemStack> content = this.getEmptyContentList(ItemStack.class);
        if (!content.isEmpty()) {
            for (Map.Entry<Vector2d, C> sectionEntry : this.content.entrySet()) {
                this.renderOnList(sectionEntry.getValue(), sectionEntry.getKey(), content);
            }
        }
        return content;
    }

    public GUISection getSectionAt(Vector2d position) {
        if (position == null) return null;
        for (Vector2d pos : this.content.keySet()) {
            C section = this.content.get(pos);
            if (pos.getX() <= position.getX() && pos.getX() + section.getWidth() - 1 >= position.getX()
                    && pos.getY() <= position.getY() && pos.getY() + section.getHeight() - 1 >= position.getY()) {
                return section.getSectionAt(position.subtract(pos));
            }
        }
        return null;
    }

    /**
     * Adds an Item to an empty slot on the pane.
     * @param item The Item
     */
    @SuppressWarnings("UnusedReturnValue")
    public S addItemEmptySlot(GUIItem item) {
        int nextEmptySlot = this.getNextEmptySlot();
        if (nextEmptySlot == -1) return (S) this;
        this.setSectionAt(nextEmptySlot, (C) item);
        return (S) this;
    }

    /**
     * Fills all empty slots with this Item.
     * @param item The Item
     */
    @SuppressWarnings("UnusedReturnValue")
    public S fill(GUIItem item) {
        while (this.getNextEmptySlot() != -1) {
            this.addItemEmptySlot(item);
        }
        return (S) this;
    }

    private int getNextEmptySlot() {
        for (int i = 0; i < this.getSlots(); i++) {
            if (this.getSectionAt(this.slotToVector(i)) == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes a section from the pane.
     * @param section The Section
     * @return Whether the section existed
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean removeSection(C section) {
        for (Vector2d vector2d : this.content.keySet()) {
            if (this.content.get(vector2d).equals(section)) {
                this.content.remove(vector2d);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all sections from the pane
     */
    @SuppressWarnings("UnusedReturnValue")
    public S clear() {
        this.content.clear();
        return (S) this;
    }

    /**
     * Sets a section at a specific slot.
     * Note that the specified slot is converted to a vector using the pane's width.
     * This can lead to exceptions when the size of this pane is zero.
     * Bear in mind that the vector conversion happens at the time calling this method, which means
     * if you change the size afterward the position will stay the same.
     * If it comes to issues you can use {@link #setSectionAt(Vector2d, GUISection)}
     */
    @SuppressWarnings("UnusedReturnValue")
    public S setSectionAt(int slot, C section) {
        return this.setSectionAt(this.slotToVector(slot), section);
    }

    /**
     * Sets a section at a specific position
     */
    public S setSectionAt(Vector2d position, C section) {
        this.content.put(position, section);
        return (S) this;
    }

    /**
     * Sets a section at coordinates 0,0
     */
    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public S setSection(C section) {
        return this.setSectionAt(new Vector2d(), section);
    }
}
