package de.placeblock.betterinventories;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import de.placeblock.betterinventories.util.Vector2d;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Example for creating an interesting inventory using GUIPanes
 * With gui.showPlayer(Player) you can show the GUI to a player!
 */
@SuppressWarnings("unused")
public class StructureExample extends ChestGUI {
    /**
     * You can instantiate your GUI as usual
     * @param plugin The Plugin that uses this GUI
     */
    public StructureExample(Plugin plugin) {
        super(plugin, Component.text("Test title"), false, 3, 3);

        // Create two Panes
        SimpleGUIPane pane1 = new SimpleGUIPane.Builder(this)
                .size(new Vector2d(4, 3))
                .build();
        SimpleGUIPane pane2 = new SimpleGUIPane.Builder(this)
                .size(new Vector2d(4, 3))
                .build();

        // Add Items to the panes
        pane1.setSection(new GUIItem.Builder(this).itemStack(new ItemStack(Material.ANVIL)).build());
        pane1.setSectionAt(2, new GUIItem.Builder(this).itemStack(new ItemStack(Material.ANVIL)).build());

        // Add the panes to the GUI
        this.canvas.setSectionAt(0, pane1);
        this.canvas.setSectionAt(5, pane2);

        // Don't forget to update the inventories
        this.update();
    }
}
