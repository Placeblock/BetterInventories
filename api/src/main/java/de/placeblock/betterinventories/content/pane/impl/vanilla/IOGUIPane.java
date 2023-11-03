package de.placeblock.betterinventories.content.pane.impl.vanilla;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * GUIPane which allows Items to be inserted and taken out
 */
@SuppressWarnings("unused")
public abstract class IOGUIPane extends SimpleItemGUIPane {

    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI for the Pane
     * @param size The size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public IOGUIPane(GUI gui, Vector2d size, boolean autoSize) {
        this(gui, size, size, autoSize);
    }
    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI for the Pane
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     */
    public IOGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize) {
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
    public IOGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize, autoSize);
    }

    @Override
    public boolean onItemAdd(Vector2d position, ItemStack itemStack) {
        this.setSectionAt(position, new GUIItem(this.getGui(), itemStack));
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () ->
                this.getGui().update(), 1);
        this.onItemChange(position);
        return false;
    }
    @Override
    public boolean onItemRemove(Vector2d position) {
        boolean removed = this.removeSection(position);
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () ->
                this.getGui().update(), 1);
        this.onItemChange(position);
        return false;
    }
    @Override
    public boolean onItemAmount(Vector2d position, int amount) {
        GUIItem item = this.getItem(position);
        item.getItemStack().setAmount(amount);
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () ->
                this.getGui().update(), 1);
        this.onItemChange(position);
        return false;
    }

    @Override
    public void onItemProvide(ItemStack itemStack) {
        ItemStack itemClone = itemStack.clone();
        for (int slot = 0; slot < this.getSlots() && itemStack.getAmount() > 0; slot++) {
            Vector2d position = this.slotToVector(slot);
            GUIItem item = this.getItem(position);
            int accepted;
            if (item != null) {
                ItemStack currentItemStack = item.getItemStack();
                int currentAmount = currentItemStack.getAmount();
                if (!currentItemStack.isSimilar(itemStack) || currentAmount >= 64) continue;
                accepted = Math.min(64-currentAmount, itemStack.getAmount());
                currentItemStack.setAmount(accepted+currentAmount);
            } else {
                accepted = Math.min(64, itemStack.getAmount());
                ItemStack slotItem = itemClone.clone();
                slotItem.setAmount(accepted);
                this.setSectionAt(slot, new GUIItem(this.getGui(), slotItem));
            }
            itemStack.setAmount(itemStack.getAmount()-accepted);
            System.out.println(itemStack.getAmount());
            this.onItemChange(position);
        }
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () ->
                this.getGui().update(), 1);
    }

    /**
     * Called when an item changes
     * @param position The position of the item that changed
     */
    public abstract void onItemChange(Vector2d position);

}
