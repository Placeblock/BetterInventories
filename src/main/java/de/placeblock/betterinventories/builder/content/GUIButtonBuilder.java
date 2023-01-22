package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class GUIButtonBuilder extends BaseGUIItemBuilder<GUIButton, GUIButtonBuilder> {
    private Consumer<Player> onClick;
    private Consumer<Player> onRightClick;
    private Consumer<Player> onLeftClick;
    private Consumer<Player> onShiftClick;
    private Consumer<Player> onShiftRightClick;
    private Consumer<Player> onShiftLeftClick;

    public GUIButtonBuilder(GUI gui) {
        super(gui);
    }

    public GUIButtonBuilder onClick(Consumer<Player> onClick) {
        this.onClick = onClick;
        return this;
    }

    public GUIButtonBuilder onLeftClick(Consumer<Player> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    public GUIButtonBuilder onRightClick(Consumer<Player> onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }


    public GUIButtonBuilder onShiftClick(Consumer<Player> onShiftClick) {
        this.onShiftClick = onShiftClick;
        return this;
    }

    public GUIButtonBuilder onShiftLeftClick(Consumer<Player> onShiftLeftClick) {
        this.onShiftLeftClick = onShiftLeftClick;
        return this;
    }

    public GUIButtonBuilder onShiftRightClick(Consumer<Player> onShiftRightClick) {
        this.onShiftRightClick = onShiftRightClick;
        return this;
    }

    protected Consumer<Player> getOnClick() {
        return this.onClick;
    }

    protected Consumer<Player> getOnLeftClick() {
        return this.onLeftClick;
    }

    protected Consumer<Player> getOnRightClick() {
        return this.onRightClick;
    }

    protected Consumer<Player> getOnShiftClick() {
        return this.onClick;
    }

    protected Consumer<Player> getOnShiftLeftClick() {
        return this.onLeftClick;
    }

    protected Consumer<Player> getOnShiftRightClick() {
        return this.onRightClick;
    }

    @Override
    public GUIButton build() {
        return new GUIButton(this.getGui(), this.getItem()) {
            @Override
            public void onClick(Player player) {
                if (GUIButtonBuilder.this.onClick == null) return;
                GUIButtonBuilder.this.onClick.accept(player);
            }
            @Override
            public void onLeftClick(Player player) {
                if (GUIButtonBuilder.this.onLeftClick == null) return;
                GUIButtonBuilder.this.onLeftClick.accept(player);
            }
            @Override
            public void onRightClick(Player player) {
                if (GUIButtonBuilder.this.onRightClick == null) return;
                GUIButtonBuilder.this.onRightClick.accept(player);
            }
            @Override
            public void onShiftClick(Player player) {
                if (GUIButtonBuilder.this.onShiftClick == null) return;
                GUIButtonBuilder.this.onShiftClick.accept(player);
            }
            @Override
            public void onShiftLeftClick(Player player) {
                if (GUIButtonBuilder.this.onShiftLeftClick == null) return;
                GUIButtonBuilder.this.onShiftLeftClick.accept(player);
            }
            @Override
            public void onShiftRightClick(Player player) {
                if (GUIButtonBuilder.this.onShiftRightClick == null) return;
                GUIButtonBuilder.this.onShiftRightClick.accept(player);
            }
        };
    }
}
