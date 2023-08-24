package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.impl.SubmitGUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class SubmitGUIButtonBuilder extends BaseGUIButtonBuilder<SubmitGUIButton, SubmitGUIButtonBuilder>{
    private ItemStack submitItem;
    private int submitDelay = 0;
    private Consumer<ClickData> onSubmit;

    public SubmitGUIButtonBuilder(GUI gui) {
        super(gui);
    }

    public SubmitGUIButtonBuilder submitItem(ItemStack item) {
        this.submitItem = item;
        return this;
    }

    public SubmitGUIButtonBuilder submitDelay(int delay) {
        this.submitDelay = delay;
        return this;
    }

    public SubmitGUIButtonBuilder onSubmit(Consumer<ClickData> callback) {
        this.onSubmit = callback;
        return this;
    }

    @Override
    public SubmitGUIButton build() {
        return new SubmitGUIButton(this.getGui(), this.getItem(), this.submitItem, this.getPermission(), this.submitDelay) {
            @Override
            public void onClick(ClickData data) {
                if (SubmitGUIButtonBuilder.this.getOnClick() == null) return;
                SubmitGUIButtonBuilder.this.getOnClick().accept(data);
            }
            @Override
            public void onLeftClick(ClickData data) {
                if (SubmitGUIButtonBuilder.this.getOnLeftClick() == null) return;
                SubmitGUIButtonBuilder.this.getOnLeftClick().accept(data);
            }
            @Override
            public void onRightClick(ClickData data) {
                if (SubmitGUIButtonBuilder.this.getOnRightClick() == null) return;
                SubmitGUIButtonBuilder.this.getOnRightClick().accept(data);
            }
            @Override
            public void onShiftClick(ClickData data) {
                if (SubmitGUIButtonBuilder.this.getOnShiftClick() == null) return;
                SubmitGUIButtonBuilder.this.getOnShiftClick().accept(data);
            }
            @Override
            public void onShiftLeftClick(ClickData data) {
                if (SubmitGUIButtonBuilder.this.getOnShiftLeftClick() == null) return;
                SubmitGUIButtonBuilder.this.getOnShiftLeftClick().accept(data);
            }
            @Override
            public void onShiftRightClick(ClickData data) {
                if (SubmitGUIButtonBuilder.this.getOnShiftRightClick() == null) return;
                SubmitGUIButtonBuilder.this.getOnShiftRightClick().accept(data);
            }
            @Override
            public void onSubmit(ClickData data) {
                if (SubmitGUIButtonBuilder.this.onSubmit == null) return;
                SubmitGUIButtonBuilder.this.onSubmit.accept(data);
            }
        };
    }
}
