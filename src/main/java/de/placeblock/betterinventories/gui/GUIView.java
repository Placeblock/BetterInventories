package de.placeblock.betterinventories.gui;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;


@Getter
public class GUIView {

    private final Player player;
    private final Inventory inventory;

    public GUIView(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        this.player.openInventory(inventory);
    }

    public void update(List<ItemStack> content) {
        this.inventory.setContents(content.toArray(ItemStack[]::new));
        this.player.updateInventory();
    }

}
