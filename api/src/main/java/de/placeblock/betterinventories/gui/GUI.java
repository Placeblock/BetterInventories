package de.placeblock.betterinventories.gui;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;


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

    /**
     * Used to provide items on MOVE_TO_OTHER_INVENTORY click event
     * @param itemStack The ItemStack that is provided
     */
    public abstract void provideItem(ItemStack itemStack);


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
        if (view == null) {
            // Item Offer
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                ItemStack offerItem = Objects.requireNonNull(event.getCurrentItem()).clone();
                this.provideItem(offerItem);
                if (offerItem.getAmount() == event.getCurrentItem().getAmount()) {
                    event.setCancelled(true);
                } else {
                    event.getCurrentItem().setAmount(offerItem.getAmount());
                }
            }
            return;
        }
        int slot = event.getSlot();
        GUISection.SearchData searchData = this.searchSection(slot, true);
        if (searchData == null) {
            event.setCancelled(true);
            return;
        }
        ItemStack currentItem = event.getCurrentItem();
        Vector2d pos = searchData.getRelativePos();
        GUISection section = searchData.getSection();
        ClickData clickData = new ClickData(player, pos, event.getAction(), event);
        section.onItemClick(clickData);
        switch (event.getAction()) {
            case PICKUP_HALF -> {
                assert currentItem != null;
                if (currentItem.getAmount() == 1) {
                    dispatchRemove(event, section, pos);
                } else {
                    int newAmount = currentItem.getAmount()/2;
                    dispatchAmount(event, section, pos, newAmount);
                }
            }
            case PICKUP_ONE, DROP_ONE_SLOT -> {
                assert currentItem != null;
                if (currentItem.getAmount() == 1) {
                    dispatchRemove(event, section, pos);
                } else {
                    dispatchAmount(event, section, pos, currentItem.getAmount()-1);
                }
            }
            case MOVE_TO_OTHER_INVENTORY, PICKUP_ALL, DROP_ALL_SLOT -> dispatchRemove(event, section, pos);
            case PLACE_ALL -> {
                if (currentItem == null) {
                    int newAmount = event.getCursor().getAmount();
                    dispatchAdd(event, section, pos, event.getCursor(), newAmount);
                } else {
                    int newAmount = currentItem.getAmount()+event.getCursor().getAmount();
                    dispatchAmount(event, section, pos, newAmount);
                }
            }
            case PLACE_ONE -> {
                if (currentItem == null) {
                    dispatchAdd(event, section, pos, event.getCursor(), 1);
                } else {
                    dispatchAmount(event, section, pos, currentItem.getAmount()+1);
                }
            }
            case HOTBAR_SWAP -> {
                if (currentItem == null) {
                    ItemStack hotbarItem = getHotbarItem(event);
                    dispatchAdd(event, section, pos, hotbarItem, hotbarItem.getAmount());
                } else {
                    dispatchRemove(event, section, pos);
                }
            }
            case HOTBAR_MOVE_AND_READD -> {
                ItemStack hotbarItem = getHotbarItem(event);
                if (hotbarItem.isSimilar(currentItem)) {
                    dispatchAmount(event, section, pos, hotbarItem.getAmount());
                } else {
                    dispatchRemove(event, section, pos);
                    dispatchAdd(event, section, pos, hotbarItem, hotbarItem.getAmount());
                }
            }
            default -> event.setCancelled(true);
        }
    }

    /**
     * Used to get hotbar items from events
     * @param event The Event
     * @return the hotbar item for the event
     */
    private static ItemStack getHotbarItem(InventoryClickEvent event) {
        Inventory bottomInventory = event.getView().getBottomInventory();
        return bottomInventory.getItem(event.getHotbarButton());
    }

    /**
     * Dispatches an add action
     * @param event The event
     * @param section The section
     * @param position The position
     * @param item The itemStack that got added
     * @param amount The amount of the itemStack
     */
    public void dispatchAdd(Cancellable event, GUISection section, Vector2d position, ItemStack item, int amount) {
        item = item.clone();
        item.setAmount(amount);
        if (section.onItemAdd(position, item)) {
            event.setCancelled(true);
        }
    }

    /**
     * Dispatches a remove action
     * @param event The event
     * @param section The section
     * @param position The position
     */
    public void dispatchRemove(Cancellable event, GUISection section, Vector2d position) {
        if (section.onItemRemove(position)) {
            event.setCancelled(true);
        }
    }

    /**
     * Dispatches an amount action
     * @param event The event
     * @param section The section
     * @param position The position
     * @param amount The new amount
     */
    public void dispatchAmount(Cancellable event, GUISection section, Vector2d position, int amount) {
        if (section.onItemAmount(position, amount)) {
            event.setCancelled(true);
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

    /**
     * Removes Items from the View
     * @param view The view
     * @param cancelledSlots The cancelled slots
     */
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

    /**
     * Returns cancelled items to the cursor
     * @param event The event
     * @param cancelledSlots The cancelled slots
     */
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

    /**
     * Calculates the cancelled slots
     * @param event The event
     * @param sectionSlots All slots according to the sections
     * @return The cancelled slots
     */
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

    /**
     * Used to get sections for each slot
     * @param guiSlots The slots
     * @return The sections that belong to the slots
     */
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
