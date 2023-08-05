package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

import java.util.*;


@SuppressWarnings("unused")
public class SimpleGUIPane extends GUIPane {
    private final Map<Vector2d, GUISection> content = new HashMap<>();
    private final boolean autoSize;

    public SimpleGUIPane(GUI gui, Vector2d size, Vector2d maxSize) {
        this(gui, size, maxSize, false);
    }

    public SimpleGUIPane(GUI gui, Vector2d size, Vector2d maxSize, boolean autoSize) {
        super(gui, size, maxSize);
        this.autoSize = autoSize;
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        if (this.autoSize) {
            Set<Vector2d> vectors = new HashSet<>();
            for (Map.Entry<Vector2d, GUISection> childEntry : this.content.entrySet()) {
                vectors.add(childEntry.getKey().add(childEntry.getValue().getSize()));
            }
            Vector2d largest = Vector2d.largest(vectors);
            this.size = Vector2d.min(Vector2d.min(largest, parentMaxSize), this.maxSize);
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
            for (Map.Entry<Vector2d, GUISection> sectionEntry : this.content.entrySet()) {
                this.renderOnList(sectionEntry.getValue(), sectionEntry.getKey(), content);
            }
        }
        return content;
    }

    public GUISection getSectionAt(Vector2d position) {
        if (position == null) return null;
        for (Vector2d pos : this.content.keySet()) {
            GUISection section = this.content.get(pos);
            if (pos.getX() <= position.getX() && pos.getX() + section.getWidth() - 1 >= position.getX()
                    && pos.getY() <= position.getY() && pos.getY() + section.getHeight() - 1 >= position.getY()) {
                return section.getSectionAt(position.subtract(pos));
            }
        }
        return null;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeSection(GUISection section) {
        for (Vector2d vector2d : this.content.keySet()) {
            if (this.content.get(vector2d).equals(section)) {
                this.content.remove(vector2d);
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
    public SimpleGUIPane clear() {
        this.content.clear();
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public SimpleGUIPane addItem(GUIItem item) {
        int nextEmptySlot = this.getNextEmptySlot();
        if (nextEmptySlot == -1) return this;
        this.setSectionAt(nextEmptySlot, item);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public SimpleGUIPane setSectionAt(int index, GUISection section) {
        return this.setSectionAt(this.slotToVector(index), section);
    }

    public SimpleGUIPane setSectionAt(Vector2d position, GUISection section) {
        this.content.put(position, section);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public SimpleGUIPane fill(GUIItem item) {
        while (this.getNextEmptySlot() != -1) {
            this.addItem(item);
        }
        return this;
    }

    private int getNextEmptySlot() {
        for (int i = 0; i < this.getSlots(); i++) {
            if (this.getSectionAt(this.slotToVector(i)) == null) {
                return i;
            }
        }
        return -1;
    }
}
