package de.placeblock.betterinventories.content.pane;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.pane.size.PanePos;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;


@Getter
@Setter
@SuppressWarnings("unused")
public abstract class GUIPane extends GUISection {

    public GUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, PanePos size) {
        super(gui, minSize, maxSize, size);
    }

    protected List<ItemStack> renderOnList(Vector2d position, GUISection section, List<ItemStack> content) {
        List<ItemStack> childContent = section.render();
        for (int i = 0; i < childContent.size(); i++) {
            Vector2d relative = section.slotToVector(i);
            content.set(this.vectorToSlot(position.add(relative)), childContent.get(i));
        }
        return content;
    }
}
