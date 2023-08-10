package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("unchecked")
public class BaseSimpleGUIPane<C extends GUISection, S extends BaseSimpleGUIPane<C, S>> extends GUIPane {
    private final Map<Vector2d, C> content = new HashMap<>();
    private final boolean autoSize;

    public BaseSimpleGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize);
        this.autoSize = autoSize;
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        if (this.autoSize) {
            Set<Vector2d> vectors = new HashSet<>();
            for (Map.Entry<Vector2d, C> childEntry : this.content.entrySet()) {
                vectors.add(childEntry.getKey().add(childEntry.getValue().getSize()));
            }
            Vector2d largest = Vector2d.largest(vectors);
            this.setSize(Vector2d.min(largest, parentMaxSize));
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

    @SuppressWarnings("UnusedReturnValue")
    public S addItemEmptySlot(GUIItem item) {
        int nextEmptySlot = this.getNextEmptySlot();
        if (nextEmptySlot == -1) return (S) this;
        this.setSectionAt(nextEmptySlot, (C) item);
        return (S) this;
    }

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

    @SuppressWarnings("UnusedReturnValue")
    public S clear() {
        this.content.clear();
        return (S) this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public S setSectionAt(int index, C section) {
        return this.setSectionAt(this.slotToVector(index), section);
    }

    public S setSectionAt(Vector2d position, C section) {
        this.content.put(position, section);
        return (S) this;
    }
}
