package de.placeblock.betterinventories.gui.listener;

import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.gui.GUIView;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Listener for GUIs that handles item interactions
 */
@Getter
@RequiredArgsConstructor
public class GUIItemListener implements Listener {

    /**
     * The GUI this listener belongs to
     */
    private final GUI gui;

    /**
     * Called by Bukkit when Player clicks an Inventory
     * @param event The Event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        GUIView view = this.gui.getView(event.getInventory());
        if (view == null) return;
        if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
            event.setCancelled(true);
            return;
        }
        view = this.gui.getView(event.getClickedInventory());
        if (view == null) {
            // Item Offer
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                ItemStack offerItem = Objects.requireNonNull(event.getCurrentItem()).clone();
                this.gui.provideItem(offerItem);
                if (offerItem.getAmount() != event.getCurrentItem().getAmount()) {
                    event.getCurrentItem().setAmount(offerItem.getAmount());
                }
                event.setCancelled(true);
            }
            return;
        }
        int slot = event.getSlot();
        this.emitClickEvent(event, player);
        SearchData searchData = new SearchData(slot, (s) -> (s instanceof GUIPane));
        this.gui.searchSection(searchData);
        if (searchData.getSection() == null) {
            event.setCancelled(true);
            return;
        }
        ItemStack currentItem = event.getCurrentItem();
        Vector2d pos = searchData.getRelativePos();
        GUISection section = searchData.getSection();
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
            case MOVE_TO_OTHER_INVENTORY -> {
                Inventory bottomInventory = event.getView().getBottomInventory();
                if (bottomInventory.firstEmpty() != -1) {
                    dispatchRemove(event, section, pos);
                    return;
                }
                assert currentItem != null;
                int availible = 0;
                for (ItemStack item : bottomInventory.getContents()) {
                    if (currentItem.isSimilar(item)) {
                        assert item != null;
                        availible += 64 - item.getAmount();
                    }
                }
                if (availible == 0) return;
                if (currentItem.getAmount() > availible) {
                    dispatchAmount(event, section, pos, currentItem.getAmount()-availible);
                } else {
                    dispatchRemove(event, section, pos);
                }
            }
            case PICKUP_ALL, DROP_ALL_SLOT -> dispatchRemove(event, section, pos);
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
            case SWAP_WITH_CURSOR -> {
                dispatchRemove(event, section, pos);
                dispatchAdd(event, section, pos, event.getCursor(), event.getCursor().getAmount());
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

    private void emitClickEvent(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.DOUBLE_CLICK) return;
        SearchData searchData = new SearchData(event.getSlot(), (s) -> true);
        this.gui.searchSection(searchData);
        if (searchData.getSection() == null) {
            return;
        }
        Vector2d pos = searchData.getRelativePos();
        ClickData clickData = new ClickData(player, pos, event.getAction(), event);
        searchData.getSection().onItemClick(clickData);
    }

    /**
     * Used to get hotbar items from events
     * @param event The Event
     * @return the hotbar item for the event
     */
    private ItemStack getHotbarItem(InventoryClickEvent event) {
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
    private void dispatchAdd(Cancellable event, GUISection section, Vector2d position, ItemStack item, int amount) {
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
        ItemStack removedItemStack = section.onItemRemove(position);
        if (removedItemStack == null) {
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
    private void dispatchAmount(Cancellable event, GUISection section, Vector2d position, int amount) {
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
        if (!(event.getWhoClicked() instanceof Player)) return;
        GUIView view = this.gui.getView(event.getInventory());
        if (view == null) return;
        List<Integer> guiSlots = event.getRawSlots().stream().filter(s -> s < this.gui.getSlots()).toList();
        // Get Sections from Slots
        Map<SearchData, Integer> sectionSlots = this.getSections(guiSlots);
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
        Bukkit.getScheduler().runTaskLater(this.gui.getPlugin(), () -> {
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
    private Map<Integer, ItemStack> getCancelledSlots(InventoryDragEvent event, Map<SearchData, Integer> sectionSlots) {
        Map<Integer, ItemStack> removedItems = new HashMap<>();
        for (SearchData searchData : sectionSlots.keySet()) {
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
    private Map<SearchData, Integer> getSections(List<Integer> guiSlots) {
        Map<SearchData, Integer> sectionSlots = new HashMap<>();
        for (Integer guiSlot : guiSlots) {
            SearchData searchData = new SearchData(guiSlot, (s) -> s instanceof GUIPane);
            this.gui.searchSection(searchData);
            sectionSlots.put(searchData, guiSlot);
        }
        return sectionSlots;
    }

}
