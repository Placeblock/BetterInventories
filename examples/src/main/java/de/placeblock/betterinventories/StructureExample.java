package de.placeblock.betterinventories;

import de.placeblock.betterinventories.builder.content.SimpleGUIPaneBuilder;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

// This example shows, how GUIPanes can be placed in GUIPanes.
@SuppressWarnings("unused")
public class StructureExample extends ChestGUI {
    public StructureExample(Plugin plugin) {
        super(plugin, Component.text("Test title"), 3);

        // Create two Panes
        SimpleGUIPane pane1 = new SimpleGUIPaneBuilder(this)
                .size(new Vector2d(4, 3))
                .build();
        SimpleGUIPane pane2 = new SimpleGUIPaneBuilder(this)
                .size(new Vector2d(4, 3))
                .build();

        // Add Items to the panes
        pane1.setSection(new GUIItem(this, new ItemStack(Material.ANVIL)));
        pane2.setSectionAt(2, new GUIItem(this, new ItemStack(Material.CHEST)));

        // Add the panes to the GUI
        this.canvas.setSectionAt(0, pane1);
        this.canvas.setSectionAt(5, pane2);

        // Don't forget to update the inventories
        this.update();
    }
}
