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
    private GUIItem inputItem;
    private GUIItem additionalItem;
    private GUIItem resultItem;

    public AnvilGUI(Plugin plugin, TextComponent title) {
        super(plugin, title, InventoryType.ANVIL);
    }

    @Override
    public Inventory createBukkitInventory() {
        return Bukkit.createInventory(null, InventoryType.ANVIL, this.getTitle());
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    protected List<ItemStack> renderContent() {
        ItemStack inputItem = this.inputItem == null ? null : this.inputItem.getItemStack();
        ItemStack additionalItem = this.additionalItem == null ? null : this.additionalItem.getItemStack();
        ItemStack resultItem = this.resultItem == null ? null : this.resultItem.getItemStack();
        return Arrays.asList(inputItem, additionalItem, resultItem);
    }

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
