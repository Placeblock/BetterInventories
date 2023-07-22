package de.placeblock.betterinventories.gui;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.item.GUIButton;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;


@Getter
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
        this.render();
        this.updateViews();
    }

    protected void render() {
        this.content = this.renderContent();
    }

    protected void updateViews() {
        for (GUIView view : this.views) {
            view.update(this.content);
        }
    }

    public List<Player> getPlayers() {
        return this.views.stream().map(GUIView::getPlayer).toList();
    }

    @SuppressWarnings("UnusedReturnValue")
    public GUIView showPlayer(Player player) {
        if (this.views.size() == 0) {
            this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        }
        if (this.getPlayers().contains(player)) return null;
        GUIView view = new GUIView(player, this.createBukkitInventory());
        view.update(this.content);
        this.views.add(view);
        return view;
    }

    private Inventory createBukkitInventory() {
        if (this.type == InventoryType.CHEST) {
            return Bukkit.createInventory(null, this.getSlots(), this.title);
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

    public void reloadViews() {
        List<Player> players = this.getPlayers();
        List<GUIView> views = new ArrayList<>(this.getViews());
        for (GUIView view : views) {
            this.removePlayer(view);
        }
        for (Player player : players) {
            this.showPlayer(player);
        }
    }

    public abstract int getSlots();
    protected abstract List<ItemStack> renderContent();
    public abstract GUISection getClickedSection(int slot);

    public void onClose(Player player) {}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getClickedInventory());
        if (view != null) {
            event.setCancelled(true);

            ClickType clickType = event.getClick();
            boolean leftClick = clickType.isLeftClick();
            boolean rightClick = clickType.isRightClick();
            if (!leftClick && !rightClick) return;

            int slot = event.getSlot();
            GUISection clicked = this.getClickedSection(slot);
            ClickData clickData = new ClickData(player, slot, event.getAction(), event);
            if (clicked instanceof GUIButton button) {
                button.click(player);
                if (event.isShiftClick()) {
                    button.onShiftClick(clickData);
                    if (leftClick) {
                        button.onShiftLeftClick(clickData);
                    } else {
                        button.onShiftRightClick(clickData);
                    }
                } else {
                    button.onClick(clickData);
                    if (leftClick) {
                        button.onLeftClick(clickData);
                    } else {
                        button.onRightClick(clickData);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view != null) {
            this.removePlayer(view);
        }
    }

    public void removePlayer(GUIView view) {
        this.views.remove(view);
        this.onClose(view.getPlayer());
        if (this.views.size() == 0) {
            HandlerList.unregisterAll(this);
        }
    }

}
