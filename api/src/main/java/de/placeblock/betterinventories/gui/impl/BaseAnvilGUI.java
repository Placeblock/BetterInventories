package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.textinput.TextInputGUI;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * GUI for creating Anvil GUIs
 * If you want to receive Text from Anvils you should use {@link TextInputGUI}
 */
@Getter
@Setter
public abstract class BaseAnvilGUI extends GUI {
    /**
     * The Item in the input-slot
     */
    private GUIItem inputItem;

    /**
     * The Item in the additional-slot
     */
    private GUIItem additionalItem;

    /**
     * The Item in the result-slot
     */
    private GUIItem resultItem;

    /**
     * Creates a new AnvilGUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param removeItems Whether to remove loose items on close.
     *                   The first player that closes the gui gets the items
     */
    protected BaseAnvilGUI(Plugin plugin, TextComponent title, boolean removeItems) {
        super(plugin, title, InventoryType.ANVIL, removeItems);
    }


    /**
     * Creates the Bukkit Inventory for this GUI
     * @return The Bukkit Inventory
     */
    @Override
    public Inventory createBukkitInventory() {
        return Bukkit.createInventory(null, InventoryType.ANVIL, this.getTitle());
    }

    /**
     * @return The amount of slots this GUI has
     */
    @Override
    public int getSlots() {
        return 3;
    }

    /**
     * Renders the GUI on a list
     * @return The List
     */
    @Override
    public List<ItemStack> renderContent() {
        ItemStack inputItem = this.inputItem == null ? null : this.inputItem.getItemStack();
        ItemStack additionalItem = this.additionalItem == null ? null : this.additionalItem.getItemStack();
        ItemStack resultItem = this.resultItem == null ? null : this.resultItem.getItemStack();
        return Arrays.asList(inputItem, additionalItem, resultItem);
    }

    /**
     * Searches the GUISection recursively. The SearchData is filled recursively.
     * @param searchData The searchData that contains all needed information
     */
    @Override
    public void searchSection(SearchData searchData) {
        switch (searchData.getSlot()) {
            case 0 -> this.inputItem.search(searchData);
            case 1 -> this.additionalItem.search(searchData);
            case 2 -> this.resultItem.search(searchData);
        }
    }

    @Override
    public void provideItem(ItemStack itemStack) {
        // Currently AnvilGUI doesn't support item offers
    }

    /**
     * The abstract Builder for BaseAnvilGUIs
     * @param <B> The Builder that implements this one
     * @param <G> The GUI that is built
     * @param <P> The plugin that uses this builder
     */
    @SuppressWarnings("unused")
    @Getter(AccessLevel.PROTECTED)
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, G, P>, G extends BaseAnvilGUI, P extends JavaPlugin> extends GUI.Builder<B, G, P> {
        private GUIItem inputItem;
        private GUIItem additionalItem;
        private GUIItem resultItem;

        /**
         * Changes the input item (The left slot)
         * @param item The new GUIItem
         * @return Itself
         */
        public B inputItem(GUIItem item) {
            this.inputItem = item;
            return this.self();
        }
        /**
         * Changes the additional item (The slot in the middle)
         * @param item The new GUIItem
         * @return Itself
         */
        public B additionalItem(GUIItem item) {
            this.additionalItem = item;
            return this.self();
        }
        /**
         * Changes the result item (The slot in the right)
         * @param item The new GUIItem
         * @return Itself
         */
        public B resultItem(GUIItem item) {
            this.resultItem = item;
            return this.self();
        }

        /**
         * Creates a new Builder
         * @param plugin The plugin that uses this builder
         */
        public AbstractBuilder(P plugin) {
            super(plugin);
        }
    }
}
