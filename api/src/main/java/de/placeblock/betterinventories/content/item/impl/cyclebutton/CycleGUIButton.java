package de.placeblock.betterinventories.content.item.impl.cyclebutton;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * GUIButton which cycles through values of an Enum
 * @param <E> The Enum
 */
public abstract class CycleGUIButton<E extends CycleEnum> extends GUIButton {
    /**
     * All Values of the Enum
     */
    private final List<E> values;
    /**
     * The current selected Enum Value
     */
    @Getter
    private E currentValue;

    /**
     * Creates a new CycleGUIButton
     * @param gui The GUI
     * @param values The Enum values
     */
    @SuppressWarnings("unused")
    public CycleGUIButton(GUI gui, E[] values) {
        this(gui, values, values[0]);
    }

    /**
     * Creates a new CycleGUIButton
     * @param gui The GUI
     * @param values The Enum values
     * @param startValue The Start-value
     */
    public CycleGUIButton(GUI gui, E[] values, E startValue) {
        super(gui, getItem(startValue));
        this.values = List.of(values);
        this.currentValue = startValue;
    }

    /**
     * Returns the Item to be displayed for an Enum-value
     * @param cycleEnum The Enum-value
     * @return The according ItemStack
     */
    private static ItemStack getItem(CycleEnum cycleEnum) {
        return new ItemBuilder(cycleEnum.getTitle(), cycleEnum.getMaterial())
                .lore(cycleEnum.getLore())
                .build();
    }

    /**
     * Cycles the Values
     * @param data The clickData if the click event
     */
    public void cycle(ClickData data) {
        int currentIndex = this.values.indexOf(this.currentValue);
        int newIndex = (++currentIndex)%this.values.size();
        this.currentValue = this.values.get(newIndex);
        this.setItemStack(getItem(this.currentValue));
        this.getGui().update();
        this.onCycle(data, this.currentValue);
    }

    /**
     * Is called when cycling through the Enum-values
     * @param data The ClickData
     * @param newValue The new Enum-value
     */
    protected abstract void onCycle(ClickData data, E newValue);
}
