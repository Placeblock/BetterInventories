package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.impl.SubmitGUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Builder for creating SubmitGUIButtons
 */
@SuppressWarnings("unused")
public class SubmitGUIButtonBuilder extends BaseGUIButtonBuilder<SubmitGUIButton, SubmitGUIButtonBuilder>{
    /**
     * The item to be displayed on submit
     */
    private ItemStack submitItem;

    /**
     *  The delay before the submit item is shown
     */
    private int submitDelay = 0;

    /**
     * Is called on submit
     */
    private Consumer<ClickData> onSubmit;

    /**
     * Creates a new SubmitGUIButtonBuilder
     * @param gui The GUI for the Button
     */
    public SubmitGUIButtonBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Sets the submit-item
     * @param item The submit-item
     * @return this
     */
    public SubmitGUIButtonBuilder submitItem(ItemStack item) {
        this.submitItem = item;
        return this;
    }

    /**
     * Sets the submit-delay
     * @param delay The submit-delay
     * @return this
     */
    public SubmitGUIButtonBuilder submitDelay(int delay) {
        this.submitDelay = delay;
        return this;
    }

    /**
     * Sets the submit Handler
     * @param callback The submit Handler
     * @return this
     */
    public SubmitGUIButtonBuilder onSubmit(Consumer<ClickData> callback) {
        this.onSubmit = callback;
        return this;
    }

    /**
     * Builds the Button
     * @return The new Button
     */
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
