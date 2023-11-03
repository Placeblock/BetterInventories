package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.ClickData;
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
 * <br>
 * How the own size is updated can be implemented by the different {@link GUIPane}s.
 */
@Getter
@Setter
@SuppressWarnings("unused")
public abstract class GUIPane extends GUISection {

    /**
     * Creates a new GUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     */
    public GUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        super(gui, minSize, minSize, maxSize);
    }

    /**
     * Sets the size and calls {@link GUIPane#onSizeChange()} if changed.
     * @param size The new size
     */
    @Override
    public void setSize(Vector2d size) {
        Vector2d oldSize = this.getSize();
        super.setSize(this.clampSize(size));
        if (!oldSize.equals(this.getSize())) this.onSizeChange();
    }

    /**
     * Sets the new height by using {@link GUIPane#setSize(Vector2d)}
     * @param height The new height
     */
    public void setHeight(int height) {
        this.setSize(new Vector2d(this.getWidth(), height));
    }


    /**
     * Sets the new width by using {@link GUIPane#setSize(Vector2d)}
     * @param width The new width
     */
    public void setWidth(int width) {
        this.setSize(new Vector2d(width, this.getHeight()));
    }

    /**
     * Is called to recursively update the size of all {@link GUIPane}s
     * @param parent The parent Pane or GUI (Sizeable)
     */
    abstract public void updateSizeRecursive(Sizeable parent);

    /**
     * Recalculates the size of the Pane when implemented
     * @param parent The parent Pane or GUI (Sizeable)
     */
    abstract public void updateSize(Sizeable parent);

    /**
     * Updates the size of all children recursive
     * @param parent The parent Pane or GUI (Sizeable)
     */
    protected void updateChildrenRecursive(Sizeable parent) {
        for (GUISection child : this.getChildren()) {
            if (child instanceof GUIPane pane) {
                pane.updateSizeRecursive(parent);
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

    @Override
    public void onItemClick(ClickData data) {}

    @Override
    public boolean onItemAdd(Vector2d position, ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean onItemRemove(Vector2d position) {
        return true;
    }

    @Override
    public boolean onItemAmount(Vector2d position, int amount) {
        return true;
    }
}
