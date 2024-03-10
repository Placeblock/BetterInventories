package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A GUISection is the most basic element that can be put inside GUIs.
 */
@Getter
@SuppressWarnings("unused")
public abstract class GUISection implements Sizeable {
    /**
     * The GUI
     */
    private final GUI gui;

    /**
     * The size of the Section
     */
    private Vector2d size;

    /**
     * The minimum size of the Section
     */
    @Setter
    protected Vector2d minSize;

    /**
     * The maximum size of the Section
     */
    @Setter
    protected Vector2d maxSize;

    /**
     * Creates a new GUISection
     * @param gui The GUI
     * @param size The size of the Section
     * @param minSize The minimum size of the Section
     * @param maxSize The maximum size of the Section
     */
    protected GUISection(GUI gui, Vector2d size, Vector2d minSize, Vector2d maxSize) {
        this.gui = gui;
        this.size = size;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    /**
     * Sets the size
     * @param vector2d The new size
     */
    public void setSize(Vector2d vector2d) {
        this.size = vector2d;
    }

    /**
     * Renders the Section on a list
     * @return The List
     */
    public abstract List<ItemStack> render();

    /**
     * Searches the GUISection recursively. The SearchData is filled recursively.
     * @param searchData The searchData that contains all needed information
     */
    public abstract void search(SearchData searchData);

    /**
     * Converts a slot to a vector based on the width of this Section
     * @param slot The slot to be converted
     * @return The calculated vector or null if the size of this Section is 0
     */
    public Vector2d slotToVector(int slot) {
        if (this.getSlots() == 0) return null;
        return Util.slotToVector(slot, this.size.getX());
    }

    /**
     * Returns a List with the amount of slots in this Section filled with null values.
     * @param clazz The class of the type of the List
     * @return The List filled with null values
     * @param <T> The type of the List
     */
    public <T> List<T> getEmptyContentList(Class<T> clazz) {
        return new ArrayList<>(Collections.nCopies(this.getSlots(), null));
    }

    /**
     * Converts a position to a slot based on the width of this Section
     * @param position The position to be converted
     * @return The calculated slot
     */
    public int vectorToSlot(Vector2d position) {
        return Util.vectorToSlot(position, this.size.getX());
    }

    /**
     * @return The slots of this Section
     */
    public int getSlots() {
        return this.size.getX()*this.size.getY();
    }

    /**
     * @return The height of this Section
     */
    public int getHeight() {
        return this.size.getY();
    }

    /**
     * @return The width of this Section
     */
    public int getWidth() {
        return this.size.getX();
    }

    /**
     * Called when a user clicks on an item.
     * Usually after this method one of the add/remove/amount methods is called.
     * @param data The clickdata
     */
    public abstract void onItemClick(ClickData data);

    /**
     * Called when an item is added to an empty slot
     * @param position The relative position of the slot
     * @param itemStack The itemstack that was added
     * @return Whether this action should be cancelled.
     */
    public abstract boolean onItemAdd(Vector2d position, ItemStack itemStack);

    /**
     * Called when an item is removed from an empty slot
     * @param position The relative position of the slot
     * @return The removed Item, if any
     */
    public abstract ItemStack onItemRemove(Vector2d position);

    /**
     * Called when the amount of an item in a slot changes
     * @param position The relative position of the slot
     * @param amount The new amount of the item
     * @return Whether this action should be cancelled.
     */
    public abstract boolean onItemAmount(Vector2d position, int amount);

    /**
     * Builder for creating various {@link GUISection}
     * @param <B> The Builder that implements this one
     * @param <P> The Product that is built
     */
    @Getter(AccessLevel.PROTECTED)
    @RequiredArgsConstructor
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B, P>, P extends GUISection> extends de.placeblock.betterinventories.Builder<B, P> {
        private final GUI gui;
        private Vector2d size;
        private Vector2d minSize;
        private Vector2d maxSize;

        /**
         * Adops the min and max size from another {@link Sizeable} e.g. {@link GUI} or {@link GUISection}
         * @param sizeable The sizeable to adopt the size from
         * @return Itself
         */
        public B adoptMinMax(Sizeable sizeable) {
            this.minSize = sizeable.getMinSize();
            this.maxSize = sizeable.getMaxSize();
            return self();
        }

        /**
         * Sets the size attribute
         * @param size The sizeable to adopt the size from
         * @return Itself
         */
        public B size(Vector2d size) {
            this.size = size;
            return self();
        }

        /**
         * Sets the size attribute
         * @param x The x part of the new size
         * @param y The y part of the new size
         * @return Itself
         */
        public B size(int x, int y) {
            return this.size(new Vector2d(x, y));
        }

        /**
         * Sets the minSize attribute
         * @param minSize The minimum size of the section
         * @return Itself
         */
        public B minSize(Vector2d minSize) {
            this.minSize = minSize;
            return self();
        }

        /**
         * Sets the minSize attribute
         * @param x The x part of the new minSize
         * @param y The y part of the new minSize
         * @return Itself
         */
        public B minSize(int x, int y) {
            return this.minSize(new Vector2d(x, y));
        }

        /**
         * Sets the maxSize attribute
         * @param maxSize The maximum size of the section
         * @return Itself
         */
        public B maxSize(Vector2d maxSize) {
            this.maxSize = maxSize;
            return self();
        }

        /**
         * Sets the maxSize attribute
         * @param x The x part of the new maxSize
         * @param y The y part of the new maxSize
         * @return Itself
         */
        public B maxSize(int x, int y) {
            return this.maxSize(new Vector2d(x, y));
        }

        /**
         * Used to get the current minimum size. Throws an exception if min-size and size are null
         * @return The current minSize
         */
        protected Vector2d getMinSize() {
            if (this.minSize == null) {
                if (this.size == null) {
                    throw new IllegalStateException("minSize and size null");
                }
                return this.size;
            }
            return this.minSize;
        }

        /**
         * Used to get the current maximum size. Throws an exception if max-size and size are null
         * @return The current maxSize
         */
        protected Vector2d getMaxSize() {
            if (this.maxSize == null) {
                if (this.size == null) {
                    throw new IllegalStateException("maxSize and size null");
                }
                return this.size;
            }
            return this.maxSize;
        }
    }
}
