package de.placeblock.betterinventories.gui;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import de.placeblock.betterinventories.content.GUISection;
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
     * @return The GUISection at the slot or null
     */
    public abstract GUISection.SearchData getClickedSection(int slot);


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
        GUISection.SearchData clickedSection = this.getClickedSection(slot);
        if (clickedSection == null) {
            event.setCancelled(true);
            return;
        }
        clickedSection.getSection().onClick(event);
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
        Multimap<GUISection.SearchData, Integer> sectionSlots = this.getSections(guiSlots);
        Map<Integer, ItemStack> cancelledSlots = this.getRemovedItems(event, sectionSlots);
        this.returnToCursor(event, cancelledSlots);
        System.out.println(cancelledSlots);
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

    private Map<Integer, ItemStack> getRemovedItems(InventoryDragEvent event, Multimap<GUISection.SearchData, Integer> sectionSlots) {
        Map<Integer, ItemStack> removedItems = new HashMap<>();
        for (GUISection.SearchData searchData : sectionSlots.keySet()) {
            // Get Items for section
            Map<Integer, ItemStack> items = new HashMap<>();
            for (Integer slot : sectionSlots.get(searchData)) {
                ItemStack newItem = event.getNewItems().get(slot).clone();
                ItemStack existingItem = event.getView().getItem(slot);
                int existingAmount = existingItem == null ? 0 : existingItem.getAmount();
                newItem.setAmount(newItem.getAmount()-existingAmount);
                items.put(slot, newItem);
            }
            InventoryDragEvent dragEvent = new InventoryDragEvent(
                    event.getView(),
                    event.getCursor(),
                    event.getOldCursor(),
                    event.getType() == DragType.SINGLE,
                    items);
            //TODO: SLOT AUS SEARCHDATA VERARBEITEN
            searchData.getSection().onDrag(dragEvent);
            if (dragEvent.isCancelled()) {
                removedItems.putAll(items);
            }
        }
        System.out.println(removedItems);
        return removedItems;
    }

    private Multimap<GUISection.SearchData, Integer> getSections(List<Integer> guiSlots) {
        Multimap<GUISection.SearchData, Integer> sectionSlots = MultimapBuilder.hashKeys().arrayListValues().build();
        for (Integer guiSlot : guiSlots) {
            GUISection.SearchData searchData = this.getClickedSection(guiSlot);
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
