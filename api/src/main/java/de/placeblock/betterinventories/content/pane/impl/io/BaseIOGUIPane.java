package de.placeblock.betterinventories.content.pane.impl.io;

import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.impl.simple.BaseSimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * GUIPane which allows Items to be inserted and taken out
 * @param <S> The implementing class to return this-type correctly e.g. {@link #addItemEmptySlot(GUIItem)}
 */
@SuppressWarnings("unused")
public class BaseIOGUIPane<S extends BaseIOGUIPane<S>> extends BaseSimpleItemGUIPane<S> {

    private final boolean input;
    private final boolean output;
    private final IOConsumer onItemChange;

    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     * @param input Whether it should be allowed to input items into the IO-Pane.
     * @param output Whether it should be allowed to remove items from the IO-Pane.
     * @param onItemChange Executed when an item in the pane changes
     */
    protected BaseIOGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize,
                            boolean input, boolean output, IOConsumer onItemChange) {
        super(gui, minSize, maxSize, autoSize);
        this.input = input;
        this.output = output;
        this.onItemChange = onItemChange;
    }

    @Override
    public boolean onItemAdd(Vector2d position, ItemStack itemStack) {
        if (!this.input) return true;
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
        if ((oldAmount < amount && !this.input) || ((oldAmount > amount) && !this.output)) return true;
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
                int maxStackSize = currentItemStack.getMaxStackSize();
                if (!currentItemStack.isSimilar(itemStack) || currentAmount >= maxStackSize) continue;
                accepted = Math.min(maxStackSize-currentAmount, itemStack.getAmount());
                currentItemStack.setAmount(accepted+currentAmount);
                this.onItemChange(position, currentItemStack);
            } else {
                accepted = Math.min(itemStack.getMaxStackSize(), itemStack.getAmount());
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
    public void onItemChange(Vector2d position, ItemStack itemStack) {
        if (this.onItemChange != null) {
            this.onItemChange.apply(position, itemStack);
        }
    }

    /**
     * Builder for {@link BaseIOGUIPane}
     * @param <B> The Builder that implements this one
     * @param <P> The Product that is Build
     */
    @Getter(AccessLevel.PROTECTED)
    public static abstract class Builder<B extends Builder<B, P>, P extends BaseIOGUIPane<P>> extends AbstractBuilder<B, P> {
        private boolean input = true;
        private boolean output = true;
        private IOConsumer onChange = (p, i) -> {};

        /**
         * Creates a new Builder
         * @param gui The GUI this Pane belongs to
         */
        public Builder(GUI gui) {
            super(gui);
        }

        /**
         * Sets the input attribute
         * @param input Whether to accept new items
         * @return Itself
         */
        public B input(boolean input) {
            this.input = input;
            return self();
        }

        /**
         * Sets the output attribute
         * @param output Whether to allow the player to remove items
         * @return Itself
         */
        public B output(boolean output) {
            this.output = output;
            return self();
        }

        /**
         * Sets the onChange attribute
         * @param onChange Called when an item changes. {@link BaseIOGUIPane#onItemChange(Vector2d, ItemStack)}
         * @return Itself
         */
        public B onChange(IOConsumer onChange) {
            this.onChange = onChange;
            return self();
        }
    }

}
