package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

import java.util.*;


@SuppressWarnings("unused")
public class SimpleGUIPane extends GUIPane {
    private final Map<Vector2d, GUISection> content = new HashMap<>();

    public SimpleGUIPane(GUI gui, Vector2d size, Vector2d maxSize) {
        super(gui, size, maxSize);
    }

    protected List<ItemStack> renderOnList(Vector2d position, GUISection section, List<ItemStack> content) {
        List<ItemStack> childContent = section.render();
        for (int i = 0; i < childContent.size(); i++) {
            Vector2d relative = section.slotToVector(i);
            content.set(this.vectorToSlot(position.add(relative)), childContent.get(i));
        }
        return content;
    }

    @Override
    public List<ItemStack> render() {
        List<ItemStack> content = this.getEmptyContentList(ItemStack.class);
        for (Map.Entry<Vector2d, GUISection> sectionEntry : this.content.entrySet()) {
            this.renderOnList(sectionEntry.getKey(), sectionEntry.getValue(), content);
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

    public GUISection removeSection(GUISection section) {
        for (Vector2d pos : this.content.keySet()) {
            if (this.content.get(pos).equals(section)) {
                return this.content.remove(pos);
            }
        }
        return null;
    }

    public void clear() {
        this.content.clear();
    }

    public void addSection(GUISection section) {
        int nextEmptySlot = this.getNextEmptySlot();
        if (nextEmptySlot == -1) return;
        this.setSectionAt(nextEmptySlot, section);
    }

    public void setSectionAt(int index, GUISection section) {
        this.setSectionAt(this.slotToVector(index), section);
    }

    public void setSectionAt(Vector2d position, GUISection section) {
        this.content.put(position, section);
    }

    public void fill(GUISection section) {
        while (this.getNextEmptySlot() != -1) {
            this.addSection(section);
        }
    }

    private int getNextEmptySlot() {
        List<Boolean> hascontentlist = this.getEmptyContentList(Boolean.class);
        for (Vector2d pos : this.content.keySet()) {
            GUISection child = this.content.get(pos);
            for (int i = 0; i < child.getSlots(); i++) {
                Vector2d relative = child.slotToVector(i);
                hascontentlist.set(this.vectorToSlot(pos.add(relative)), true);
            }
        }
        for (int i = 0; i < hascontentlist.size(); i++) {
            if (hascontentlist.get(i) == null) return i;
        }
        return -1;
    }
}
