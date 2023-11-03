package de.placeblock.betterinventories.gui;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.ClickData;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Root class for GUIs
 * Can be used to create any sort of GUI that can be rendered to a list
 * e.g. Chest, Hopper, Furnace, Anvil, Brewing.
 * However, for most of these examples there exist better methods for creating a GUI already.
 */
@Getter
@SuppressWarnings("unused")
public abstract class GUI implements Listener {
    /**
     * The plugin
     */
    private final Plugin plugin;

    /**
     * The title of the GUI
     */
    private final TextComponent title;

    /**
     * The current Views of the GUI
     */
    private final List<GUIView> views = new ArrayList<>();

    /**
     * The type of the GUI
     */
    private final InventoryType type;

    /**
     * The current rendered content of the GUI
     */
    private List<ItemStack> content = new ArrayList<>();

    /**
     * Creates a new GUI
     * @param plugin The plugin
     * @param title The title of the GUI
     * @param type The type of the GUI
     */
    public GUI(Plugin plugin, TextComponent title, InventoryType type) {
        this.plugin = plugin;
        this.type = type;
        this.title = title;
    }

    /**
     * Creates a new Bukkit Inventory for the GUI when implemented
     * @return The Bukkit Inventory
     */
    public abstract Inventory createBukkitInventory();


    //  VIEW AND PLAYER MANAGEMENT

    /**
     * Shows the GUI to a player.
     * @param player The Player
     * @return The newly created GUIView
     */
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

    /**
     * @return All players, which can see the GUI
     */
    public List<Player> getPlayers() {
        return this.views.stream().map(GUIView::getPlayer).toList();
    }

    /**
     * Returns the GUIView for the according Inventory
     * @param inventory The Inventory
     * @return The GUIView
     */
    public GUIView getView(Inventory inventory) {
        for (GUIView view : this.views) {
            if (view.getInventory().equals(inventory)) {
                return view;
            }
        }
        return null;
    }
    /**
     * Returns the GUIView of the player
     * @param player The Player
     * @return The GUIView
     */
    public GUIView getView(Player player) {
        for (GUIView view : this.views) {
            if (view.getPlayer().equals(player)) {
                return view;
            }
        }
        return null;
    }

    /**
     * Reloads all Views (Removes all Players and adds all Players).
     * Needed when resizing the GUI or changing the GUI's title
     */
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

    /**
     * @return The amount of slots this GUI has
     */
    public abstract int getSlots();

    /**
     * Renders the GUI on a list
     * @return The List
     */
    public abstract List<ItemStack> renderContent();

    /**
     * Returns the GUISection at a specific slot.
     * @param slot The slot
     * @param onlyPanes Whether to return only panes even
     *                  if there is an item at the clicked slot
     * @return The GUISection at the slot or null
     */
    public abstract GUISection.SearchData searchSection(int slot, boolean onlyPanes);


    //  UPDATE AND RENDERING

    /**
     * Updates the GUI, renders the GUI and updates the Views
     */
    public void update() {
        this.render();
        this.updateViews();
    }

    /**
     * Renders the GUI
     */
    protected void render() {
        this.content = this.renderContent();
    }

    /**
     * Updates the content of all Views
     */
    protected void updateViews() {
        for (GUIView view : this.views) {
            view.update(this.content);
        }
    }

    //  INTERACTION HANDLING

    /**
     * Called by Bukkit when Player clicks an Inventory
     * @param event The Event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view == null) return;
        if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
            event.setCancelled(true);
            return;
        }
        view = this.getView(event.getClickedInventory());
        if (view == null) return;
        int slot = event.getSlot();
        GUISection.SearchData searchData = this.searchSection(slot, true);
        if (searchData == null) {
            event.setCancelled(true);
            return;
        }
        ItemStack currentItem = event.getCurrentItem();
        ClickData clickData = new ClickData(player, searchData.getRelativePos(), event.getAction(), event);
        GUISection section = searchData.getSection();
        section.onItemClick(clickData);
        GUIAction action;
        int newAmount = 0;
        switch (event.getAction()) {
            case PICKUP_HALF -> {
                assert currentItem != null;
                if (currentItem.getAmount() == 1) {
                    action = GUIAction.REMOVE;
                } else {
                    newAmount = currentItem.getAmount()/2;
                    action = GUIAction.AMOUNT;
                }
            }
            case PICKUP_ONE, DROP_ONE_SLOT -> {
                assert currentItem != null;
                if (currentItem.getAmount() == 1) {
                    action = GUIAction.REMOVE;
                } else {
                    newAmount = currentItem.getAmount()-1;
                    action = GUIAction.AMOUNT;
                }
            }
            case MOVE_TO_OTHER_INVENTORY, PICKUP_ALL, DROP_ALL_SLOT -> action = GUIAction.REMOVE;
            case PLACE_ALL -> {
                if (currentItem == null) {
                    action = GUIAction.ADD;
                    newAmount = event.getCursor().getAmount();
                } else {
                    action = GUIAction.AMOUNT;
                    newAmount = currentItem.getAmount()+event.getCursor().getAmount();
                }
            }
            case PLACE_ONE -> {
                if (currentItem == null) {
                    action = GUIAction.ADD;
                    newAmount = 1;
                } else {
                    action = GUIAction.AMOUNT;
                    newAmount = currentItem.getAmount()+1;
                }
            }
            case HOTBAR_SWAP -> {
                if (currentItem == null) {
                    event.setCancelled(true);
                    return;
                }
                action = GUIAction.REMOVE;
            }
            default -> {
                event.setCancelled(true);
                return;
            }
        }
        switch (action) {
            case ADD -> {
                ItemStack item = event.getCursor().clone();
                item.setAmount(newAmount);
                if (section.onItemAdd(searchData.getRelativePos(), item)) {
                    event.setCancelled(true);
                }
            }
            case REMOVE -> {
                if (section.onItemRemove(searchData.getRelativePos())) {
                    event.setCancelled(true);
                }
            }
            case AMOUNT -> {
                if (section.onItemAmount(searchData.getRelativePos(), newAmount)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * Called by Bukkit when Player drags an Inventory
     * @param event The Event
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view == null) return;
        List<Integer> guiSlots = event.getRawSlots().stream().filter(s -> s < this.getSlots()).toList();
        // Get Sections from Slots
        Map<GUISection.SearchData, Integer> sectionSlots = this.getSections(guiSlots);
        Map<Integer, ItemStack> cancelledSlots = this.getCancelledSlots(event, sectionSlots);
        this.returnToCursor(event, cancelledSlots);
        this.removeItems(view, cancelledSlots);
    }

    private void removeItems(GUIView view, Map<Integer, ItemStack> cancelledSlots) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            for (Integer slot : cancelledSlots.keySet()) {
                ItemStack item = view.getInventory().getItem(slot);
                if (item != null) {
                    int amount = cancelledSlots.get(slot).getAmount();
                    item.setAmount(item.getAmount()- amount);
                }
            }
        }, 1);
    }

    private void returnToCursor(InventoryDragEvent event, Map<Integer, ItemStack> cancelledSlots) {
        ItemStack cursor = event.getCursor();
        for (Integer slot : cancelledSlots.keySet()) {
            ItemStack itemStack = cancelledSlots.get(slot);
            if (cursor != null) {
                cursor.setAmount(cursor.getAmount()+ itemStack.getAmount());
            } else {
                cursor = itemStack.clone();
            }
        }
        event.setCursor(cursor);
    }

    private Map<Integer, ItemStack> getCancelledSlots(InventoryDragEvent event, Map<GUISection.SearchData, Integer> sectionSlots) {
        Map<Integer, ItemStack> removedItems = new HashMap<>();
        for (GUISection.SearchData searchData : sectionSlots.keySet()) {
            GUISection section = searchData.getSection();
            int slot = sectionSlots.get(searchData);
            ItemStack newItem = event.getNewItems().get(slot).clone();
            ItemStack existingItem = event.getView().getItem(slot);
            if (existingItem == null) {
                if (section.onItemAdd(searchData.getRelativePos(), newItem)) {
                    removedItems.put(slot, newItem);
                }
            } else {
                if (section.onItemAmount(searchData.getRelativePos(), newItem.getAmount())) {
                    newItem.setAmount(newItem.getAmount()-existingItem.getAmount());
                    removedItems.put(slot, newItem);
                }
            }
        }
        return removedItems;
    }

    private Map<GUISection.SearchData, Integer> getSections(List<Integer> guiSlots) {
        Map<GUISection.SearchData, Integer> sectionSlots = new HashMap<>();
        for (Integer guiSlot : guiSlots) {
            GUISection.SearchData searchData = this.searchSection(guiSlot, true);
            sectionSlots.put(searchData, guiSlot);
        }
        return sectionSlots;
    }


    //  HANDLE GUI REMOVAL

    /**
     * Called by Bukkit when a Player closes an Inventory
     * @param event The Event
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        GUIView view = this.getView(event.getInventory());
        if (view != null) {
            this.removePlayer(view);
        }
    }

    /**
     * Removes a player without closing the Inventory of the Player
     * @param view The View of the Player
     */
    private void removePlayer(GUIView view) {
        this.views.remove(view);
        this.onClose(view.getPlayer());
        if (this.views.size() == 0) {
            HandlerList.unregisterAll(this);
        }
    }

    /**
     * Is called when the player closes the GUI.
     * @param player The player, who closed the GUI
     */
    public void onClose(Player player) {}

}
