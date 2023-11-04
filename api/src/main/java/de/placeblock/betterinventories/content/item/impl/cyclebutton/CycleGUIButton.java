package de.placeblock.betterinventories.content.item.impl.cyclebutton;

import de.placeblock.betterinventories.content.item.BaseGUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * GUIButton which cycles through values of an Enum
 * @param <E> The Enum
 */
@SuppressWarnings("unused")
public abstract class CycleGUIButton<E extends CycleEnum> extends BaseGUIButton {
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
     * @param startValue The Start-value
     */
    public CycleGUIButton(GUI gui, int cooldown, Sound sound, E[] values, E startValue) {
        super(gui, null, cooldown, sound, null);
        this.values = List.of(values);
        this.currentValue = startValue;
        this.updateItem();
    }

    /**
     * Returns the Item to be displayed for an Enum-value
     * Can be overridden for modifying Enum-Item-conversion
     * @param value The Enum-value
     * @return The according ItemStack
     */
    protected ItemStack getItem(E value) {
        return new ItemBuilder(value.getTitle(), value.getMaterial())
            .lore(value.getLore())
            .build();
    }

    @Override
    public void onClick(ClickData data) {
        this.cycle(data);
    }

    /**
     * Cycles the Values
     * @param data The clickData if the click event
     */
    @SuppressWarnings("unused")
    public void cycle(ClickData data) {
        this.currentValue = this.getNextValue(data.getPlayer(), this.currentValue, this.getNextValue(this.currentValue));
        this.updateItem();
        this.getGui().update();
        this.onCycle(data, this.currentValue);
    }

    /**
     * Calculates the new Value considering the permissions
     * Skips values if the player does not have permission
     * @param player The player to check the permission against
     * @param startValue The start value
     * @param nextValue The next value to check
     * @return The new Value
     */
    private E getNextValue(Player player, E startValue, E nextValue) {
        if (nextValue == startValue) return startValue;
        String permission = nextValue.getPermission();
        if (permission == null || player.hasPermission(permission)) {
            return nextValue;
        } else {
            return this.getNextValue(player, startValue, this.getNextValue(nextValue));
        }
    }

    /**
     * Returns the value with increased index
     * Jumps to zero if it exceeds the enum size
     * @param currentValue The current value
     * @return The next value
     */
    private E getNextValue(E currentValue) {
        int currentIndex = this.values.indexOf(currentValue);
        int newIndex = (++currentIndex)%this.values.size();
        return this.values.get(newIndex);
    }

    /**
     * Shortcut for updating the ItemStack with the new Enum-Value
     */
    private void updateItem() {
        this.setItemStack(this.getItem(this.currentValue));
    }

    /**
     * Is called when cycling through the Enum-values
     * @param data The ClickData
     * @param newValue The new Enum-value
     */
    protected abstract void onCycle(ClickData data, E newValue);

    public static class Builder<E extends CycleEnum> extends BaseGUIButton.Builder<Builder<E>, CycleGUIButton<E>> {
        private final E[] values;

        private E startValue;

        private BiConsumer<ClickData, E> onCycle;

        public Builder(GUI gui, E[] values) {
            super(gui);
            this.values = values;
        }

        public Builder<E> startValue(E startValue) {
            this.startValue = startValue;
            return this;
        }

        public Builder<E> onCycle(BiConsumer<ClickData, E> callback) {
            this.onCycle = callback;
            return this;
        }

        @Override
        public CycleGUIButton<E> build() {
            return new CycleGUIButton<>(this.getGui(), this.getCooldown(), this.getSound(), this.values, this.startValue) {
                @Override
                protected void onCycle(ClickData data, E newValue) {
                    if (Builder.this.onCycle != null) {
                        Builder.this.onCycle.accept(data, newValue);
                    }
                }
            };
        }

        @Override
        protected Builder<E> self() {
            return this;
        }
    }

}
