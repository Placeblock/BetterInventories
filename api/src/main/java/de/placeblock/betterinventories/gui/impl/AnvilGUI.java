package de.placeblock.betterinventories.gui.impl;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.impl.textinput.TextInputGUI;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

/**
 * GUI for creating Anvil GUIs
 * If you want to receive Text from Anvils you should use {@link TextInputGUI}
 */
@Getter
@Setter
public class AnvilGUI extends GUI {
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
     */
    public AnvilGUI(Plugin plugin, TextComponent title) {
        super(plugin, title, InventoryType.ANVIL);
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
     * Returns the GUISection at a specific slot.
     * @param slot The slot
     * @return The GUISection at the slot or null
     */
    @Override
    public GUISection getClickedSection(int slot) {
        return switch (slot) {
            case 0 -> this.inputItem;
            case 1 -> this.additionalItem;
            case 2 -> this.resultItem;
            default -> null;
        };
    }
}
