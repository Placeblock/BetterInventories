package de.placeblock.betterinventories.gui.impl.cartography;

import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.cartography.renderer.ImageMapRenderer;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * GUI For the cartography table
 */
public class CartographyGUI extends GUI {
    /**
     * Creates a new GUI
     *
     * @param plugin      The plugin
     * @param title       The title of the GUI
     * @param type        The type of the GUI
     * @param removeItems Whether to remove loose items on close.
     *                    The first player that closes the gui gets the items
     */
    protected CartographyGUI(Plugin plugin, TextComponent title, InventoryType type, boolean removeItems) {
        super(plugin, title, type, removeItems);
    }

    @Override
    public Inventory createBukkitInventory() {
        ItemStack itemStack = new ItemStack(Material.MAP);
        MapMeta itemMeta = (MapMeta) itemStack.getItemMeta();
        itemMeta.getMapView().addRenderer(new ImageMapRenderer());
        return Bukkit.createInventory(null, InventoryType.CARTOGRAPHY, this.getTitle());
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    protected List<ItemStack> renderContent() {
        return null;
    }

    @Override
    public void searchSection(SearchData searchData) {

    }

    @Override
    public void provideItem(ItemStack itemStack) {

    }
}
