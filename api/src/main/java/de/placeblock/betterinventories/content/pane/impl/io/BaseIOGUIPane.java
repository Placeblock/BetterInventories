package de.placeblock.betterinventories.content.pane.impl.io;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.BaseSimpleGUIPane;
import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

/**
 * GUIPane which allows Items to be inserted and taken out
 */
@SuppressWarnings("unused")
public abstract class BaseIOGUIPane extends SimpleItemGUIPane {

    private final boolean input;
    private final boolean output;

    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     * @param input Whether it should be allowed to input items into the IO-Pane.
     * @param output Whether it should be allowed to remove items from the IO-Pane.
     */
    protected BaseIOGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize, boolean input, boolean output) {
        super(gui, minSize, maxSize, autoSize);
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean onItemAdd(Vector2d position, ItemStack itemStack) {
        if (!this.input) return false;
        this.setSectionAt(position, new GUIItem.Builder(this.getGui()).itemStack(itemStack).build());
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> {
            this.onItemChange(position, itemStack);
            this.getGui().update();
        }, 1);
        return false;
    }
    @Override
    public ItemStack onItemRemove(Vector2d position) {
        if (!this.output) return null;
        GUIItem item = this.getItem(position);
        boolean removed = this.removeSection(item);
        if (removed) {
            Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> {
                this.onItemChange(position, null);
                this.getGui().update();
            }, 1);
            return item.getItemStack();
        }
        return null;
    }
    @Override
    public boolean onItemAmount(Vector2d position, int amount) {
        GUIItem item = this.getItem(position);
        int oldAmount = item.getItemStack().getAmount();
        if ((oldAmount < amount && !this.input) || ((oldAmount > amount) && !output)) return false;
        item.getItemStack().setAmount(amount);
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () -> {
            this.onItemChange(position, item.getItemStack());
            this.getGui().update();
        }, 1);
        return false;
    }

    @Override
    public void onItemProvide(ItemStack itemStack) {
        if (!this.input) return;
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
                this.onItemChange(position, currentItemStack);
            } else {
                accepted = Math.min(64, itemStack.getAmount());
                ItemStack slotItem = itemClone.clone();
                slotItem.setAmount(accepted);
                this.setSectionAt(slot, new GUIItem.Builder(this.getGui()).itemStack(slotItem).build());
                this.onItemChange(position, slotItem);
            }
            itemStack.setAmount(itemStack.getAmount()-accepted);
        }
        Bukkit.getScheduler().runTaskLater(this.getGui().getPlugin(), () ->
                this.getGui().update(), 1);
    }

    /**
     * Called when an item changes
     * @param position The position of the item that changed
     * @param itemStack The new ItemStack
     */
    public abstract void onItemChange(Vector2d position, ItemStack itemStack);

    public static abstract class Builder<B extends Builder<B, P>, P extends BaseIOGUIPane> extends BaseSimpleGUIPane.Builder<B, P> {
        private boolean input = true;
        private boolean output = true;
        private BiConsumer<Vector2d, ItemStack> onChange = (p, i) -> {};

        public Builder(GUI gui) {
            super(gui);
        }

        public B input(boolean input) {
            this.input = input;
            return self();
        }

        public B output(boolean output) {
            this.output = output;
            return self();
        }

        public B onChange(BiConsumer<Vector2d, ItemStack> onChange) {
            this.onChange = onChange;
            return self();
        }

        protected boolean isInput() {
            return this.input;
        }

        protected boolean isOutput() {
            return this.output;
        }

        protected BiConsumer<Vector2d, ItemStack> getOnChange() {
            return this.onChange;
        }
    }

}
