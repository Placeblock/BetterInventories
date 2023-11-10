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
     */
    public SwitchGUIButton(GUI gui, ItemStack item, int cooldown, Sound sound, String permission, Function<Player, GUI> targetGUI) {
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
    protected static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends SwitchGUIButton> extends GUIButton.AbstractBuilder<B, P> {
        private Function<Player, GUI> targetGUI;

        public AbstractBuilder(GUI gui) {
            super(gui);
        }

        public B targetGUI(Function<Player, GUI> target) {
            this.targetGUI = target;
            return this.self();
        }
    }
    public static class Builder extends AbstractBuilder<Builder, SwitchGUIButton> {
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
