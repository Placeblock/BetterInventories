package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;

public class GUIButtonBuilder extends BaseGUIButtonBuilder<GUIButton, GUIButtonBuilder> {

    public GUIButtonBuilder(GUI gui) {
        super(gui);
    }


    @Override
    public GUIButton build() {
        return new GUIButton(this.getGui(), this.getItem(), this.getCooldown(), this.getClickSound(), this.getPermission()) {
            @Override
            public void onClick(ClickData data) {
                if (GUIButtonBuilder.this.getOnClick() == null) return;
                GUIButtonBuilder.this.getOnClick().accept(data);
            }
            @Override
            public void onLeftClick(ClickData data) {
                if (GUIButtonBuilder.this.getOnLeftClick() == null) return;
                GUIButtonBuilder.this.getOnLeftClick().accept(data);
            }
            @Override
            public void onRightClick(ClickData data) {
                if (GUIButtonBuilder.this.getOnRightClick() == null) return;
                GUIButtonBuilder.this.getOnRightClick().accept(data);
            }
            @Override
            public void onShiftClick(ClickData data) {
                if (GUIButtonBuilder.this.getOnShiftClick() == null) return;
                GUIButtonBuilder.this.getOnShiftClick().accept(data);
            }
            @Override
            public void onShiftLeftClick(ClickData data) {
                if (GUIButtonBuilder.this.getOnShiftLeftClick() == null) return;
                GUIButtonBuilder.this.getOnShiftLeftClick().accept(data);
            }
            @Override
            public void onShiftRightClick(ClickData data) {
                if (GUIButtonBuilder.this.getOnShiftRightClick() == null) return;
                GUIButtonBuilder.this.getOnShiftRightClick().accept(data);
            }
        };
    }
}
