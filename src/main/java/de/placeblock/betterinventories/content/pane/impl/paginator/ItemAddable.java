package de.placeblock.betterinventories.content.pane.impl.paginator;

import de.placeblock.betterinventories.content.item.GUIItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public interface ItemAddable<O extends ItemAddable<O>> {

    List<GUIItem> getItems();

    @SuppressWarnings("unchecked")
    default O addItem(GUIItem item) {
        this.getItems().add(item);
        this.onItemAdd();
        return (O) this;
    }

    @SuppressWarnings("unchecked")
    default O addItems(Collection<GUIItem> items) {
        this.getItems().addAll(items);
        this.onItemAdd();
        return (O) this;
    }

    default O addItems(GUIItem... items) {
        return this.addItems(List.of(items));
    }

    default <T> O addItems(Function<T, GUIItem> converter, Collection<T> items) {
        List<GUIItem> guiItems = new ArrayList<>();
        for (T item : items) {
            guiItems.add(converter.apply(item));
        }
        return this.addItems(guiItems);
    }

    default <T> O addItems(Function<T, GUIItem> converter, T[] items) {
        return this.addItems(converter, new ArrayList<>(List.of(items)));
    }

    default void onItemAdd() {

    }

}
