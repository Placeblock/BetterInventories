package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class SimpleGUIPane extends GUIPane {
    private final Map<Vector2d, GUISection> content = new HashMap<>();

    public SimpleGUIPane(GUI gui, Vector2d size) {
        this(gui, size, size, size, false);
    }

    public SimpleGUIPane(GUI gui, Vector2d size, Vector2d maxSize, Vector2d minSize, boolean autoSize) {
        super(gui, size, maxSize, minSize, autoSize);
    }

    @Override
    public List<ItemStack> render() {
        List<ItemStack> content = this.getEmptyContentArray(ItemStack.class);
        for (Vector2d vector2d : this.content.keySet()) {
            GUISection guiSection = this.content.get(vector2d);
            content = this.renderOnList(vector2d, guiSection, content);
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
        if (this.isAutoSize()) {
            this.updateSize();
        }
    }

    public void fill(GUISection section) {
        while (this.getNextEmptySlot() != -1) {
            this.addSection(section);
        }
    }

    private int getNextEmptySlot() {
        List<Boolean> hascontentlist = this.getEmptyContentArray(Boolean.class);
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

    private void updateSize() {
        int newHeight = 0;
        int newWidth = 0;
        for (Vector2d pos : this.content.keySet()) {
            GUISection section = this.content.get(pos);
            newHeight = Math.max(newHeight, pos.getY()+ section.getHeight());
            newWidth = Math.max(newWidth, pos.getX()+ section.getWidth());
        }
        this.setHeight(newHeight);
        this.setWidth(newWidth);
    }
}
