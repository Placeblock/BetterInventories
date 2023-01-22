package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class GUIButtonBuilder extends BaseGUIItemBuilder<GUIButton, GUIButtonBuilder> {
    private BiConsumer<Player, Boolean> onClick;
    private BiConsumer<Player, Boolean> onRightClick;
    private BiConsumer<Player, Boolean> onLeftClick;

    public GUIButtonBuilder(GUI gui) {
        super(gui);
    }

    public GUIButtonBuilder onClick(BiConsumer<Player, Boolean> onClick) {
        this.onClick = onClick;
        return this;
    }

    public GUIButtonBuilder onLeftClick(BiConsumer<Player, Boolean> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    public GUIButtonBuilder onRightClick(BiConsumer<Player, Boolean> onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    @Override
    public GUIButton build() {
        return null;
    }
}
