package de.placeblock.betterinventories.content.item.impl.cyclebutton;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class CycleGUIButton<E extends CycleEnum> extends GUIButton {
    private final List<E> values;
    @Getter
    private E currentValue;

    public CycleGUIButton(GUI gui, E[] values, E startValue) {
        super(gui, getItem(startValue));
        this.values = List.of(values);
        this.currentValue = startValue;
    }

    private static ItemStack getItem(CycleEnum cycleEnum) {
        return new ItemBuilder(cycleEnum.getTitle(), cycleEnum.getMaterial())
                .lore(cycleEnum.getLore())
                .build();
    }

    @Override
    public void onClick(ClickData data) {
        int currentIndex = this.values.indexOf(this.currentValue);
        int newIndex = (++currentIndex)%this.values.size();
        this.currentValue = this.values.get(newIndex);
        this.setItemStack(getItem(this.currentValue));
        this.getGui().update();
        this.onCycle(data, this.currentValue);
    }

    abstract void onCycle(ClickData data, E newValue);
}
