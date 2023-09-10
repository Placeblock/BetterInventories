package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.interaction.InteractionHandler;
import de.placeblock.betterinventories.util.Util;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;

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
     * The registered InteractionHandlers
     */
    protected final List<InteractionHandler> interactionHandlers = new ArrayList<>();


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
     * Returns the GUISection at a specific slot.
     * @param slot The slot
     * @return The GUISection at the slot or null
     */
    public GUISection getSectionAt(int slot) {
        return this.getSectionAt(this.slotToVector(slot));
    }

    /**
     * Returns the GUISection at a specific position.
     * @param position The position
     * @return The GUISection at the slot or null
     */
    public abstract GUISection getSectionAt(Vector2d position);

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
     * Registers a new InteractionHandler.
     * InteractionHandlers will receive Inventory Click- and DragEvents
     * @param handler The handler
     */
    public void registerInteractionHandler(InteractionHandler handler) {
        this.interactionHandlers.add(handler);
    }

    /**
     * Unregisters a new InteractionHandler
     * @param handler The handler
     */
    public void unregisterInteractionHandler(InteractionHandler handler) {
        this.interactionHandlers.remove(handler);
    }

    /**
     * Calls the InteractionHandlers
     * @param handler Handler callback. Handler calling breaks if Handler callback returns true
     */
    public void handleInteraction(Function<InteractionHandler, Boolean> handler) {
        for (InteractionHandler interactionHandler : this.interactionHandlers) {
            boolean processed = handler.apply(interactionHandler);
            if (processed) break;
        }
    }
}
