package de.placeblock.betterinventories.content.item.impl;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

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
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param targetGUI The GUI to be opened
     */
    public SwitchGUIButton(GUI gui, ItemStack item, Supplier<GUI> targetGUI) {
        this(gui, item, player -> targetGUI.get());
    }

    /**
     * Creates a new SwitchGUIButton
     * The creation of the targetGUI can use the player which clicked the button
     * @param gui The GUI
     * @param item The ItemStack of the Button
     * @param targetGUI The GUI to be opened
     */
    public SwitchGUIButton(GUI gui, ItemStack item, Function<Player, GUI> targetGUI) {
        super(gui, item, targetGUI == null ? null : Sound.UI_BUTTON_CLICK);
        this.targetGUI = targetGUI;
    }

    @Override
    public void onClick(ClickData data) {
        if (this.targetGUI != null) {
            Player player = data.getPlayer();
            this.targetGUI.apply(player).showPlayer(player);
        }
    }
}
