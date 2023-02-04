package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.entity.Player;

/**
 * Author: Placeblock
 */
public class GUIButtonBuilder extends BaseGUIButtonBuilder<GUIButton, GUIButtonBuilder> {

    public GUIButtonBuilder(GUI gui) {
        super(gui);
    }


    @Override
    public GUIButton build() {
        return new GUIButton(this.getGui(), this.getItem()) {
            @Override
            public void onClick(Player player, int slot) {
                if (GUIButtonBuilder.this.getOnClick() == null) return;
                GUIButtonBuilder.this.getOnClick().accept(player, slot);
            }
            @Override
            public void onLeftClick(Player player, int slot) {
                if (GUIButtonBuilder.this.getOnLeftClick() == null) return;
                GUIButtonBuilder.this.getOnLeftClick().accept(player, slot);
            }
            @Override
            public void onRightClick(Player player, int slot) {
                if (GUIButtonBuilder.this.getOnRightClick() == null) return;
                GUIButtonBuilder.this.getOnRightClick().accept(player, slot);
            }
            @Override
            public void onShiftClick(Player player, int slot) {
                if (GUIButtonBuilder.this.getOnShiftClick() == null) return;
                GUIButtonBuilder.this.getOnShiftClick().accept(player, slot);
            }
            @Override
            public void onShiftLeftClick(Player player, int slot) {
                if (GUIButtonBuilder.this.getOnShiftLeftClick() == null) return;
                GUIButtonBuilder.this.getOnShiftLeftClick().accept(player, slot);
            }
            @Override
            public void onShiftRightClick(Player player, int slot) {
                if (GUIButtonBuilder.this.getOnShiftRightClick() == null) return;
                GUIButtonBuilder.this.getOnShiftRightClick().accept(player, slot);
            }
        };
    }
}
