package de.placeblock.betterinventories.content.pane.impl;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.builder.content.HorizontalSplitGUIPaneBuilder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A {@link GUIPane} with an upper and lower pane.
 * Renders the lower pane always below the upper pane.
 * <br>
 * Builder: {@link HorizontalSplitGUIPaneBuilder}
 */
@Setter
public class HorizontalSplitGUIPane extends GUIPane {
    /**
     * The upper Pane or null
     */
    private GUIPane upperPane;
    /**
     * The lower Pane or null
     */
    private GUIPane lowerPane;

    /**
     * Creates a new HorizontalSplitGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     */
    public HorizontalSplitGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        super(gui, minSize, maxSize);
    }

    /**
     * Is called to recursively update the size of all {@link GUIPane}s
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSizeRecursive(Sizeable parent) {
        if (this.upperPane != null) {
            this.upperPane.updateSizeRecursive(parent);
        }
        if (this.lowerPane != null) {
            this.lowerPane.updateSizeRecursive(parent);
        }
        this.updateSize(parent);
    }

    /**
     * Updates the size of the Pane based on the height of upper and lower Pane.
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSize(Sizeable parent) {
        int width = this.getNewWidth();
        int height = this.getNewHeight();
        Vector2d newSize = new Vector2d(width, height);
        this.setSize(Vector2d.min(this.maxSize, newSize));
    }

    /**
     * @return The new width taking upper and lower panes into account
     */
    private int getNewWidth() {
        return Math.max(this.upperPane == null ? 0 : this.upperPane.getWidth(),
                this.lowerPane == null ? 0 : this.lowerPane.getWidth());
    }

    /**
     * @return The new height taking upper and lower panes into account
     */
    private int getNewHeight() {
        return (this.upperPane == null ? 0 : this.upperPane.getHeight()) +
                (this.lowerPane == null ? 0 : this.lowerPane.getHeight());
    }

    /**
     * @return All children (upper and lower Pane)
     */
    @Override
    public Set<GUISection> getChildren() {
        Set<GUISection> children = new HashSet<>();
        if (this.upperPane != null) {
            children.add(this.upperPane);
        }
        if (this.lowerPane != null) {
            children.add(this.lowerPane);
        }
        return children;
    }

    /**
     * Renders the Pane on a list
     * @return The List
     */
    @Override
    public List<ItemStack> render() {
        List<ItemStack> content = this.getEmptyContentList(ItemStack.class);
        if (!content.isEmpty()) {
            if (this.upperPane != null) {
                this.renderOnList(this.upperPane, new Vector2d(), content);
            }
            if (this.lowerPane != null) {
                this.renderOnList(this.lowerPane, this.getLowerPanePos(), content);
            }
        }
        return content;
    }

    /**
     * @return The position of the lower Pane taking the height of the upper Pane into account
     */
    public Vector2d getLowerPanePos() {
        if (this.upperPane == null) return new Vector2d();
        return new Vector2d(0, this.upperPane.getHeight());
    }

    /**
     * Returns the GUISection at a specific position.
     * @param position The position
     * @param onlyPanes Whether to return only panes even
     *                  if there is an item at the clicked slot
     * @return The GUISection
     */
    @Override
    public SearchData search(Vector2d position, boolean onlyPanes) {
        if (this.upperPane == null) {
            if (this.lowerPane == null) return null;
            return this.lowerPane.search(position.subtract(this.getLowerPanePos()), onlyPanes);
        } else {
            if (this.lowerPane == null ||
                position.getY() < this.upperPane.getHeight()) {
                return this.upperPane.search(position, onlyPanes);
            } else {
                return this.lowerPane.search(position.subtract(this.getLowerPanePos()), onlyPanes);
            }
        }
    }
}
