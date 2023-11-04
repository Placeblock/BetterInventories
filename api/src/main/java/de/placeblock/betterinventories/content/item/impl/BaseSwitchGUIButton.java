package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.BaseGUIButton;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

/**
 * A {@link BaseGUIButton} which automatically switches the GUI to another {@link GUI}.
 */
@SuppressWarnings("unused")
public abstract class BaseSwitchGUIButton extends BaseGUIButton {
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
     */
    public BaseSwitchGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, Function<Player, GUI> targetGUI) {
        super(gui, item, cooldown, sound, permission);
        this.targetGUI = targetGUI;
    }

    @Override
    public void onClick(ClickData data) {
        if (this.targetGUI != null) {
            Player player = data.getPlayer();
            this.targetGUI.apply(player).showPlayer(player);
        }
    }

    @Getter(AccessLevel.PROTECTED)
    public static abstract class Builder<B extends Builder<B, P>, P extends BaseSwitchGUIButton> extends BaseGUIButton.Builder<B, P> {
        private Function<Player, GUI> targetGUI;

        public Builder(GUI gui) {
            super(gui);
        }

        public B targetGUI(Function<Player, GUI> target) {
            this.targetGUI = target;
            return this.self();
        }
    }
}
