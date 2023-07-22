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

    public SimpleGUIPane(GUI gui, Vector2d maxSize) {
        super(gui, new Vector2d(), maxSize);
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        Set<Vector2d> vectors = this.content.keySet();
        Vector2d largest = Vector2d.largest(vectors);
        this.size = Vector2d.min(Vector2d.min(largest, parentMaxSize), this.maxSize);
    }

    @Override
    public Set<GUISection> getChildren() {
        return new HashSet<>(this.content.values());
    }

    protected List<ItemStack> renderOnList(GUISection section, Vector2d position, List<ItemStack> content) {
        List<ItemStack> childContent = section.render();
        for (int i = 0; i < childContent.size(); i++) {
            Vector2d relative = section.slotToVector(i);
            Vector2d absolute = position.add(relative);
            content.set(this.vectorToSlot(absolute), childContent.get(i));
        }
        return content;
    }

    @Override
    public List<ItemStack> render() {
        List<ItemStack> content = this.getEmptyContentList(ItemStack.class);
        for (Map.Entry<Vector2d, GUISection> sectionEntry : this.content.entrySet()) {
            this.renderOnList(sectionEntry.getValue(), sectionEntry.getKey(), content);
        }
        return content;
    }

    public GUISection getSectionAt(Vector2d position) {
        for (Vector2d pos : this.content.keySet()) {
            GUISection section = this.content.get(pos);
            if (pos.getX() <= position.getX() && pos.getX() + section.getWidth() - 1 >= position.getX()
                    && pos.getY() <= position.getY() && pos.getY() + section.getHeight() - 1 >= position.getY()) {
                return section.getSectionAt(position.subtract(pos));
            }
        }
        return null;
    }

    public boolean removeSection(GUISection section) {
        for (Vector2d vector2d : this.content.keySet()) {
            if (this.content.get(vector2d).equals(section)) {
                this.content.remove(vector2d);
                return true;
            }
        }
        return false;
    }

    public SimpleGUIPane clear() {
        this.content.clear();
        return this;
    }

    public SimpleGUIPane addItem(GUIItem item) {
        int nextEmptySlot = this.getNextEmptySlot();
        if (nextEmptySlot == -1) return this;
        this.setSectionAt(nextEmptySlot, item);
        return this;
    }

    public SimpleGUIPane setSectionAt(int index, GUISection section) {
        return this.setSectionAt(this.slotToVector(index), section);
    }

    public SimpleGUIPane setSectionAt(Vector2d position, GUISection section) {
        this.content.put(position, section);
        return this;
    }

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
