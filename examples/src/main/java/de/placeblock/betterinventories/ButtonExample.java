package de.placeblock.betterinventories;

import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.CanvasGUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Example for creating a CanvasGUI with some Buttons.
 * We use Hopper Inventory in this example (Not mandatory).
 * With gui.showPlayer(Player) you can show the GUI to a player!
 */

@SuppressWarnings("unused")
public class ButtonExample extends CanvasGUI {
    /**
     * You can instantiate your GUI as usual
     * @param plugin The Plugin that uses this GUI
     */
    protected ButtonExample(Plugin plugin) {
        super(plugin, Component.text("Test title"), InventoryType.HOPPER, true);

        // There are multiple ways to create Buttons
        this.canvas.setSectionAt(1, new ExampleButton(this)); // Example Button created below
        this.canvas.setSectionAt(2, new GUIButton.Builder(this) // Using Button Builder
                .itemStack(new ItemStack(Material.ACACIA_BUTTON))
                .onClick(data -> {})
                .build());

        // Don't forget to update the inventories
        this.update();
    }

    // You don't have to derive the GUIButton, but because of re-usability it is recommended.
    private static class ExampleButton extends GUIButton {
        public ExampleButton(GUI gui) {
            // We use the ItemBuilder to create the ItemStack
            super(gui, new ItemBuilder(Component.text("Test Button"), Material.ACACIA_BUTTON).build(),
                    0, Sound.UI_TOAST_IN, null);
        }

        @Override
        public void onClick(ClickData data) {

        }

        // Several other click methods can be overridden
    }
}
