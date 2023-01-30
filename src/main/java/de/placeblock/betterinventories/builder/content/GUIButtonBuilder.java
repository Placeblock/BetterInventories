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
    private BiConsumer<Player, Integer> onClick;
    private BiConsumer<Player, Integer> onRightClick;
    private BiConsumer<Player, Integer> onLeftClick;
    private BiConsumer<Player, Integer> onShiftClick;
    private BiConsumer<Player, Integer> onShiftRightClick;
    private BiConsumer<Player, Integer> onShiftLeftClick;

    public GUIButtonBuilder(GUI gui) {
        super(gui);
    }

    public GUIButtonBuilder onClick(BiConsumer<Player, Integer> onClick) {
        this.onClick = onClick;
        return this;
    }

    public GUIButtonBuilder onLeftClick(BiConsumer<Player, Integer> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    public GUIButtonBuilder onRightClick(BiConsumer<Player, Integer> onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }


    public GUIButtonBuilder onShiftClick(BiConsumer<Player, Integer> onShiftClick) {
        this.onShiftClick = onShiftClick;
        return this;
    }

    public GUIButtonBuilder onShiftLeftClick(BiConsumer<Player, Integer> onShiftLeftClick) {
        this.onShiftLeftClick = onShiftLeftClick;
        return this;
    }

    public GUIButtonBuilder onShiftRightClick(BiConsumer<Player, Integer> onShiftRightClick) {
        this.onShiftRightClick = onShiftRightClick;
        return this;
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
        return this.onClick;
    }

    protected BiConsumer<Player, Integer> getOnShiftLeftClick() {
        return this.onLeftClick;
    }

    protected BiConsumer<Player, Integer> getOnShiftRightClick() {
        return this.onRightClick;
    }

    @Override
    public GUIButton build() {
        return new GUIButton(this.getGui(), this.getItem()) {
            @Override
            public void onClick(Player player, int slot) {
                if (GUIButtonBuilder.this.onClick == null) return;
                GUIButtonBuilder.this.onClick.accept(player, slot);
            }
            @Override
            public void onLeftClick(Player player, int slot) {
                if (GUIButtonBuilder.this.onLeftClick == null) return;
                GUIButtonBuilder.this.onLeftClick.accept(player, slot);
            }
            @Override
            public void onRightClick(Player player, int slot) {
                if (GUIButtonBuilder.this.onRightClick == null) return;
                GUIButtonBuilder.this.onRightClick.accept(player, slot);
            }
            @Override
            public void onShiftClick(Player player, int slot) {
                if (GUIButtonBuilder.this.onShiftClick == null) return;
                GUIButtonBuilder.this.onShiftClick.accept(player, slot);
            }
            @Override
            public void onShiftLeftClick(Player player, int slot) {
                if (GUIButtonBuilder.this.onShiftLeftClick == null) return;
                GUIButtonBuilder.this.onShiftLeftClick.accept(player, slot);
            }
            @Override
            public void onShiftRightClick(Player player, int slot) {
                if (GUIButtonBuilder.this.onShiftRightClick == null) return;
                GUIButtonBuilder.this.onShiftRightClick.accept(player, slot);
            }
        };
    }
}
