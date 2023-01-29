package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Author: Placeblock
 */
@Getter
@Setter
@SuppressWarnings("unused")
public abstract class GUIPane extends GUISection {
    private Vector2d maxSize;
    private Vector2d minSize;
    private boolean autoSize;

    public GUIPane(GUI gui, Vector2d size) {
        this(gui, size, size, size, false);
    }

    public GUIPane(GUI gui, Vector2d size, Vector2d maxSize, Vector2d minSize, boolean autoSize) {
        super(gui, size);
        this.maxSize = maxSize;
        this.minSize = minSize;
        this.autoSize = autoSize;
    }

    protected List<ItemStack> renderOnList(Vector2d position, GUISection section, List<ItemStack> content) {
        List<ItemStack> childContent = section.render();
        for (int i = 0; i < childContent.size(); i++) {
            Vector2d relative = section.slotToVector(i);
            content.set(this.vectorToSlot(position.add(relative)), childContent.get(i));
        }
        return content;
    }

    public void checkUpdateSize() {
        if (!this.autoSize) return;
        this.updateSize();
    }
    protected void updateSize() {}

    @Override
    public void setHeight(int height) {
        super.setHeight(Math.max(Math.min(height, this.maxSize.getY()), this.minSize.getY()));
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(Math.max(Math.min(width, this.maxSize.getX()), this.minSize.getX()));
    }
}
