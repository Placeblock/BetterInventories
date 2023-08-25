package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Interface for classes where items can be added to a list.
 * @param <O> The implementing class for returning this correctly
 */
@SuppressWarnings("unused")
public interface ItemAddable<O extends ItemAddable<O>> {

    /**
     * Has to return all Items currently added
     * @return All added Items
     */
    List<GUIItem> getItems();

    /**
     * Adds an Item.
     * @param item The Item
     * @return this
     */
    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    default O addItem(GUIItem item) {
        this.getItems().add(item);
        this.onItemAdd();
        return (O) this;
    }

    /**
     * Adds multiple Items.
     * @param items The Items
     * @return this
     */
    @SuppressWarnings("unchecked")
    default O addItems(Collection<GUIItem> items) {
        this.getItems().addAll(items);
        this.onItemAdd();
        return (O) this;
    }

    /**
     * Adds multiple Items.
     * @param items The Items
     * @return this
     */
    default O addItems(GUIItem... items) {
        return this.addItems(List.of(items));
    }

    /**
     * Adds multiple Items.
     * @param converter The converter to convert the generic type to a {@link GUIItem}
     * @param items The Items
     * @param <T> The type of the Items
     * @return this
     */
    default <T> O addItems(Function<T, GUIItem> converter, Collection<T> items) {
        List<GUIItem> guiItems = new ArrayList<>();
        for (T item : items) {
            guiItems.add(converter.apply(item));
        }
        return this.addItems(guiItems);
    }

    /**
     * Adds multiple Items.
     * @param converter The converter to convert the generic type to a {@link GUIItem}
     * @param items The Items
     * @param <T> The type of the Items
     * @return this
     */
    default <T> O addItems(Function<T, GUIItem> converter, T[] items) {
        return this.addItems(converter, new ArrayList<>(List.of(items)));
    }

    /**
     * Can be overridden and is called when items were added.
     */
    default void onItemAdd() {

    }

}
