package de.placeblock.betterinventories.content.item.impl.cyclebutton;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * GUIButton which cycles through values of an Enum
 * @param <E> The Enum
 */
@SuppressWarnings("unused")
public class CycleGUIButton<E extends CycleEnum> extends GUIButton {
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
     * Called when a player cycles the values
     */
    private final CycleConsumer<E> onCycle;

    /**
     * Creates a new CycleGUIButton.
     * The permission is passed in the various enum values.
     * @param gui The GUI
     * @param values The Enum values
     * @param startValue The Start-value
     * @param cooldown The cooldown of the Button. Applied to the material, not the Button alone.
     * @param sound The sound that is played when pressing that button
     * @param onCycle Called when a player cycles the values
     */
    public CycleGUIButton(GUI gui, int cooldown, Sound sound, E[] values, E startValue, CycleConsumer<E> onCycle) {
        super(gui, null, cooldown, sound, null);
        this.values = List.of(values);
        this.currentValue = startValue;
        this.onCycle = onCycle;
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
        this.currentValue = this.getNextValue(data.player(), this.currentValue, this.getNextValue(this.currentValue));
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
    protected void onCycle(ClickData data, E newValue) {
        if (this.onCycle != null) {
            this.onCycle.apply(data, newValue);
        }
    }

    /**
     * Builder for creating {@link CycleGUIButton}
     * @param <E> The enum that is cycled through
     */
    public static class Builder<E extends CycleEnum> extends AbstractBuilder<Builder<E>, CycleGUIButton<E>> {
        private final E[] values;

        private E startValue;

        private CycleConsumer<E> onCycle;

        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         * @param values All possible enum values
         */
        public Builder(GUI gui, E[] values) {
            super(gui);
            this.values = values;
        }

        /**
         * Sets the startValue attribute
         * @param startValue The value this button starts with
         * @return Itself
         */
        public Builder<E> startValue(E startValue) {
            this.startValue = startValue;
            return this;
        }

        /**
         * Sets the onCycle attribute
         * @param callback Is called when cycling through the Enum-values
         * @return Itself
         */
        public Builder<E> onCycle(CycleConsumer<E> callback) {
            this.onCycle = callback;
            return this;
        }

        @Override
        public CycleGUIButton<E> build() {
            return new CycleGUIButton<>(this.getGui(), this.getCooldown(), this.getSound(),
                    this.values, this.startValue, this.onCycle);
        }

        @Override
        protected Builder<E> self() {
            return this;
        }
    }

}
