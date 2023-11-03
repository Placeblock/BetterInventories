package de.placeblock.betterinventories.content.pane.impl.transfer;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * GUIPane which allows Items to be inserted and taken out
 */
@SuppressWarnings("unused")
public class TransferGUIPane extends SimpleItemGUIPane {
    /**
     * After inventoryevents the slot can only be updated one tick later
     * to prevent item errors we lock the slot to block further access until it has
     * been set correctly.
     */
    private final Collection<Vector2d> locked = new ArrayList<>();

    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI for the Pane
     * @param size The size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public TransferGUIPane(GUI gui, Vector2d size, boolean autoSize) {
        this(gui, size, size, true);
    }
    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI for the Pane
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     */
    public TransferGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
        this(gui, minSize, maxSize, true);
    }

    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public TransferGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
    }

    @Override
    public boolean onItemAdd(Vector2d position, ItemStack itemStack) {
        if (this.locked.contains(position)) return true;
        this.locked.add(position);
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> {
            this.setSectionAt(position, new GUIItem(this.getGui(), itemStack));
            this.getGui().update();
            this.locked.remove(position);
        }, 1);
        return false;
    }
    @Override
    public boolean onItemRemove(Vector2d position) {
        if (this.locked.contains(position)) return true;
        this.locked.add(position);
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> {
            boolean removed = this.removeSection(position);
            this.getGui().update();
            this.locked.remove(position);
        }, 1);
        return false;
    }
    @Override
    public boolean onItemAmount(Vector2d position, int amount) {
        if (this.locked.contains(position)) return true;
        this.locked.add(position);
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> {
            GUIItem item = this.getItem(position);
            item.getItemStack().setAmount(amount);
            this.getGui().update();
            this.locked.remove(position);
        }, 1);
        return false;
    }

    @Override
    public void onItemProvide(ItemStack itemStack) {
        ItemStack itemClone = itemStack.clone();
        Map<Integer, Integer> acceptedItems = new HashMap<>();
        for (int slot = 0; slot < this.getSlots() && itemStack.getAmount() > 0; slot++) {
            GUIItem item = this.getItem(slot);
            int accepted;
            if (item != null) {
                ItemStack currentItemStack = item.getItemStack();
                int currentAmount = currentItemStack.getAmount();
                if (!currentItemStack.isSimilar(itemStack) || currentAmount >= 64) continue;
                accepted = Math.min(64-currentAmount, itemStack.getAmount());
            } else {
                accepted = Math.min(64, itemStack.getAmount());
            }
            acceptedItems.put(slot, accepted);
            itemStack.setAmount(itemStack.getAmount()-accepted);
            this.locked.add(this.slotToVector(slot));
        }
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> {
            for (Integer slot : acceptedItems.keySet()) {
                int amount = acceptedItems.get(slot);
                GUIItem item = this.getItem(slot);
                if (item == null) {
                    ItemStack slotItem = itemClone.clone();
                    slotItem.setAmount(amount);
                    this.setSectionAt(slot, new GUIItem(this.getGui(), slotItem));
                } else {
                    item.getItemStack().setAmount(amount);
                }
                this.getGui().update();
                this.locked.remove(this.slotToVector(slot));
            }
        }, 1);
    }

}
