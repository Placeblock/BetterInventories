package de.placeblock.betterinventories;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorControlsPosition;
import de.placeblock.betterinventories.content.pane.impl.paginator.PaginatorGUIPane;
import de.placeblock.betterinventories.gui.impl.ChestGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

// We use ChestGUI to auto-resize the GUI
// With gui.showPlayer(Player) you can show the GUI to a player!
@SuppressWarnings("unused")
public class PaginatorExample extends ChestGUI {
    public PaginatorExample(Plugin plugin) {
        // Specifies minHeight and maxHeight to allow auto-resize
        super(plugin, Component.text("Test Title"), true, 1, 6);

        // Creates the PaginatorGUIPane with the according Builder
        PaginatorGUIPane paginator = new PaginatorGUIPane.Builder(this)
                // Paginator uses min and max size of the canvas
                .adoptMinMax(this.canvas)
                // This line adds controls to the Paginator
                .controls(PaginatorControlsPosition.RIGHT)
                .build();
        // Adds the paginator to the canvas
        this.canvas.setSection(paginator);
        // Adds an item to the paginator
        GUIItem item = new GUIItem.Builder(this)
                .itemStack(new ItemStack(Material.STONE))
                .build();
        paginator.addItem(item);
        // Don't forget to update the inventories
        this.update();
    }
}
