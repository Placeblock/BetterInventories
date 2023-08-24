package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;


/**
 * A {@link GUISection} that can contain other {@link GUISection}s.
 * Renders to a List.
 * <p></p>
 * Updating the {@link GUIPane}'s size works as follows:
 * At first the size of the children is updated, then the own size.
 * To modify this update process you can override {@link #updateSizeRecursive(Vector2d)}.
 * How the own size is updated can be implemented by the different {@link GUIPane}s.
 */
@Getter
@Setter
@SuppressWarnings("unused")
public abstract class GUIPane extends GUISection {

    public GUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        super(gui, minSize, minSize, maxSize);
    }

    @Override
    public void setSize(Vector2d size) {
        Vector2d oldSize = this.getSize();
        super.setSize(this.clampSize(size));
        if (!oldSize.equals(this.getSize())) this.onSizeChange();
    }

    public void setHeight(int height) {
        this.setSize(new Vector2d(this.getWidth(), height));
    }

    public void setWidth(int width) {
        this.setSize(new Vector2d(width, this.getHeight()));
    }

    abstract public void updateSizeRecursive(Vector2d parentMaxSize);

    abstract public void updateSize(Vector2d parentMaxSize);

    protected void updateChildrenRecursive(Vector2d parentMaxSize) {
        for (GUISection child : this.getChildren()) {
            if (child instanceof GUIPane pane) {
                pane.updateSizeRecursive(parentMaxSize);
            }
        }
    }

    /**
     * Can be overridden and is only called when the size of this GUIPane really changes.
     */
    public void onSizeChange() {

    }

    /**
     * Implemented by GUIPanes
     * Should return all children sections
     * @return All child sections
     */
    abstract public Set<GUISection> getChildren();

    /**
     * Renders a child section at a specific position on a list
     * @param section The child section
     * @param position The child position the child is at
     * @param content The list on which the section should be rendered. Has to have the size of this GUIPane,
     *                otherwise it can lead to exceptions.
     */
    protected void renderOnList(GUISection section, Vector2d position, List<ItemStack> content) {
        List<ItemStack> childContent = section.render();
        for (int i = 0; i < childContent.size(); i++) {
            Vector2d relative = section.slotToVector(i);
            Vector2d absolute = position.add(relative);
            if (absolute.getX() < this.getWidth() &&
                absolute.getY() < this.getHeight()) {
                int slot = this.vectorToSlot(absolute);
                ItemStack item = childContent.get(i);
                content.set(slot, item);
            }
        }
    }
}
