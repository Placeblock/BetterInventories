package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

/**
 * A {@link GUIButton} which automatically switches the GUI to another {@link GUI}.
 */
@SuppressWarnings("unused")
public class SwitchGUIButton extends GUIButton {
    /**
     * The GUI to be opened
     */
    private final Function<Player, GUI> targetGUI;

    /**
     * Creates a new SwitchGUIButton
     * The creation of the targetGUI can use the player which clicked the button
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param targetGUI The GUI to be opened
     * @param cooldown The cooldown of the Button
     * @param permission The permission required to press this button
     * @param sound The sound played when pressing this button
     */
    protected SwitchGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, Function<Player, GUI> targetGUI) {
        super(gui, item, cooldown, sound, permission);
        this.targetGUI = targetGUI;
    }

    @Override
    public void onClick(ClickData data) {
        if (this.targetGUI != null) {
            Player player = data.player();
            this.targetGUI.apply(player).showPlayer(player);
        }
    }

    /**
     * Abstract Builder for creating {@link SwitchGUIButton}
     * @param <B> The Builder that implements this one
     * @param <P> The {@link GUIButton} that is built
     */
    @Getter(AccessLevel.PROTECTED)
    protected static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends SwitchGUIButton> extends GUIButton.AbstractBuilder<B, P> {
        private Function<Player, GUI> targetGUI;

        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        protected AbstractBuilder(GUI gui) {
            super(gui);
        }

        /**
         * Sets the targetGUI attribute
         * @param target The target GUI to open on click
         * @return Itself
         */
        public B targetGUI(Function<Player, GUI> target) {
            this.targetGUI = target;
            return this.self();
        }
    }

    /**
     * Builder for creating {@link SwitchGUIButton}
     */
    public static class Builder extends AbstractBuilder<Builder, SwitchGUIButton> {
        /**
         * Creates a new Builder
         * @param gui The gui this button belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        @Override
        public SwitchGUIButton build() {
            return new SwitchGUIButton(this.getGui(), this.getItemStack(), this.getCooldown(),
                    this.getSound(), this.getPermission(), this.getTargetGUI());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
