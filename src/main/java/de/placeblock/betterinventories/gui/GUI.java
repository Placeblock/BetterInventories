package de.placeblock.betterinventories.gui;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.GUIButton;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public abstract class GUI implements Listener {

    private final Plugin plugin;
    private final TextComponent title;
    private final List<GUIView> views = new ArrayList<>();
    private final InventoryType type;
    private List<ItemStack> content = new ArrayList<>();

    public GUI(Plugin plugin, TextComponent title, InventoryType type) {
        this.plugin = plugin;
        this.type = type;
        this.title = title;
    }

    public void update() {
        this.content = this.render();
        for (GUIView view : this.views) {
            view.update(this.content);
        }
    }

    public void showPlayer(Player player) {
        if (this.views.size() == 0) {
            this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        }
        GUIView view = new GUIView(player, this.createBukkitInventory());
        view.update(this.content);
        this.views.add(view);

    }

    private Inventory createBukkitInventory() {
        if (this.type == InventoryType.CHEST) {
            return Bukkit.createInventory(null, this.getSize(), this.title);
        } else {
            return Bukkit.createInventory(null, this.type, this.title);
        }
    }

    public GUIView getView(Inventory inventory) {
        for (GUIView view : this.views) {
            if (view.getInventory().equals(inventory)) {
                return view;
            }
        }
        return null;
    }

    public abstract int getSize();
    public abstract List<ItemStack> render();
    public abstract GUISection getClickedSection(int slot);

    public void onClose(Player player) {}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getClickedInventory());
        if (view != null) {
            event.setCancelled(true);

            InventoryAction action = event.getAction();
            boolean leftClick = action == InventoryAction.PICKUP_ALL;
            boolean rightClick = action == InventoryAction.PICKUP_HALF;
            if (!leftClick && !rightClick) return;

            int slot = event.getSlot();
            GUISection clicked = this.getClickedSection(slot);
            if (clicked instanceof GUIButton button) {
                button.onClick(player);
                if (leftClick) {
                    button.onLeftClick(player);
                } else {
                    button.onRightClick(player);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view != null) {
            this.views.remove(view);
            this.onClose(player);
            if (this.views.size() == 0) {
                HandlerList.unregisterAll(this);
            }
        }
    }


}
