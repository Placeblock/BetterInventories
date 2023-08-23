package de.placeblock.betterinventories;

import de.placeblock.betterinventories.builder.content.GUIButtonBuilder;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.CanvasGUI;
import de.placeblock.betterinventories.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

// We use Hopper Inventory in this example (Not mandatory)
@SuppressWarnings("unused")
public class ButtonExample extends CanvasGUI {
    protected ButtonExample(Plugin plugin) {
        super(plugin, Component.text("Test title"), InventoryType.HOPPER);

        // There are multiple ways to create Buttons
        this.canvas.setSectionAt(1, new ExampleButton(this)); // Example Button created below
        this.canvas.setSectionAt(2, new GUIButtonBuilder(this) // Using Button Builder
                .item(new ItemStack(Material.ACACIA_BUTTON))
                .onClick(data -> {})
                .build());

        // Don't forget to update the inventories
        this.update();
    }

    // You don't have to derive the GUIButton, but because of re-usability it is recommended.
    private static class ExampleButton extends GUIButton {
        public ExampleButton(GUI gui) {
            // We use the ItemBuilder to create the ItemStack
            super(gui, new ItemBuilder(Component.text("Test Button"), Material.ACACIA_BUTTON).build());
        }

        @Override
        public void onClick(ClickData data) {

        }

        // Several other click methods can be overridden
    }
}
