package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.item.ClickData;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
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
    protected Vector2d minSize;

    /**
     * The maximum size of the Section
     */
    protected Vector2d maxSize;

    /**
     * Creates a new GUISection
     * @param gui The GUI
     * @param size The size of the Section
     * @param minSize The minimum size of the Section
     * @param maxSize The maximum size of the Section
     */
    public GUISection(GUI gui, Vector2d size, Vector2d minSize, Vector2d maxSize) {
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
     * @return Whether this action is allowed.
     */
    public abstract boolean onItemAdd(Vector2d position, ItemStack itemStack);

    /**
     * Called when an item is removed from an empty slot
     * @param position The relative position of the slot
     * @return Whether this action is allowed.
     */
    public abstract boolean onItemRemove(Vector2d position);

    /**
     * Called when the amount of an item in a slot changes
     * @param position The relative position of the slot
     * @param amount The new amount of the item
     * @return Whether this action is allowed.
     */
    public abstract boolean onItemAmount(Vector2d position, int amount);
}
