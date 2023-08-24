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
 * <p></p>
 * Builder: {@link HorizontalSplitGUIPaneBuilder}
 */
@Setter
public class HorizontalSplitGUIPane extends GUIPane {
    private GUIPane upperPane;
    private GUIPane lowerPane;

    public HorizontalSplitGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        super(gui, minSize, maxSize);
    }

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

    @Override
    public void updateSize(Sizeable parent) {
        int width = this.getNewWidth();
        int height = this.getNewHeight();
        Vector2d newSize = new Vector2d(width, height);
        this.setSize(Vector2d.min(this.maxSize, newSize));
    }

    private int getNewWidth() {
        return Math.max(this.upperPane == null ? 0 : this.upperPane.getWidth(),
                this.lowerPane == null ? 0 : this.lowerPane.getWidth());
    }
    private int getNewHeight() {
        return (this.upperPane == null ? 0 : this.upperPane.getHeight()) +
                (this.lowerPane == null ? 0 : this.lowerPane.getHeight());
    }

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

    public Vector2d getLowerPanePos() {
        if (this.upperPane == null) return new Vector2d();
        return new Vector2d(0, this.upperPane.getHeight());
    }

    @Override
    public GUISection getSectionAt(Vector2d position) {
        if (this.upperPane == null) {
            if (this.lowerPane == null) return null;
            return this.lowerPane.getSectionAt(position.subtract(this.getLowerPanePos()));
        } else {
            if (this.lowerPane == null ||
                position.getY() < this.upperPane.getHeight()) {
                return this.upperPane.getSectionAt(position);
            } else {
                return this.lowerPane.getSectionAt(position.subtract(this.getLowerPanePos()));
            }
        }
    }
}
