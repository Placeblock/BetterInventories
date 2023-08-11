package de.placeblock.betterinventories.content.pane.impl;

import de.placeblock.betterinventories.builder.content.HorizontalSplitGUIPaneBuilder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

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
    public void updateSizeRecursive(Vector2d parentMaxSize) {
        this.upperPane.updateSizeRecursive(this.maxSize);
        this.lowerPane.updateSizeRecursive(this.maxSize);
        this.updateSize(parentMaxSize);
    }

    @Override
    public void updateSize(Vector2d parentMaxSize) {
        int width = Math.max(this.upperPane.getWidth(), this.lowerPane.getWidth());
        int height = this.upperPane.getHeight() + this.lowerPane.getHeight();
        Vector2d potSize = new Vector2d(width, height);
        this.setSize(Vector2d.min(this.maxSize, Vector2d.min(parentMaxSize, potSize)));
    }

    @Override
    public Set<GUISection> getChildren() {
        return Set.of(this.lowerPane, this.upperPane);
    }

    @Override
    public List<ItemStack> render() {
        List<ItemStack> content = this.getEmptyContentList(ItemStack.class);
        if (!content.isEmpty()) {
            this.renderOnList(this.upperPane, new Vector2d(), content);
            this.renderOnList(this.lowerPane, this.getLowerPanePos(), content);
        }
        return content;
    }

    public Vector2d getLowerPanePos() {
        return new Vector2d(0, this.upperPane.getHeight());
    }

    @Override
    public GUISection getSectionAt(Vector2d position) {
        if (position.getY() >= this.upperPane.getHeight()) {
            return this.lowerPane.getSectionAt(position.subtract(this.getLowerPanePos()));
        } else {
            return this.upperPane.getSectionAt(position);
        }
    }
}
