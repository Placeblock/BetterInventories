package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;


@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseGUIButtonBuilder<G extends GUIButton, B extends BaseGUIButtonBuilder<G, B>> extends BaseGUIItemBuilder<G, B> {
    private BiConsumer<Player, Integer> onClick;
    private BiConsumer<Player, Integer> onRightClick;
    private BiConsumer<Player, Integer> onLeftClick;
    private BiConsumer<Player, Integer> onShiftClick;
    private BiConsumer<Player, Integer> onShiftRightClick;
    private BiConsumer<Player, Integer> onShiftLeftClick;

    public BaseGUIButtonBuilder(GUI gui) {
        super(gui);
    }

    public B onClick(BiConsumer<Player, Integer> onClick) {
        this.onClick = onClick;
        return (B) this;
    }

    public B onLeftClick(BiConsumer<Player, Integer> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return (B) this;
    }

    public B onRightClick(BiConsumer<Player, Integer> onRightClick) {
        this.onRightClick = onRightClick;
        return (B) this;
    }


    public B onShiftClick(BiConsumer<Player, Integer> onShiftClick) {
        this.onShiftClick = onShiftClick;
        return (B) this;
    }

    public B onShiftLeftClick(BiConsumer<Player, Integer> onShiftLeftClick) {
        this.onShiftLeftClick = onShiftLeftClick;
        return (B) this;
    }

    public B onShiftRightClick(BiConsumer<Player, Integer> onShiftRightClick) {
        this.onShiftRightClick = onShiftRightClick;
        return (B) this;
    }

    protected BiConsumer<Player, Integer> getOnClick() {
        return this.onClick;
    }

    protected BiConsumer<Player, Integer> getOnLeftClick() {
        return this.onLeftClick;
    }

    protected BiConsumer<Player, Integer> getOnRightClick() {
        return this.onRightClick;
    }

    protected BiConsumer<Player, Integer> getOnShiftClick() {
        return this.onShiftClick;
    }

    protected BiConsumer<Player, Integer> getOnShiftLeftClick() {
        return this.onShiftLeftClick;
    }

    protected BiConsumer<Player, Integer> getOnShiftRightClick() {
        return this.onShiftRightClick;
    }
}
