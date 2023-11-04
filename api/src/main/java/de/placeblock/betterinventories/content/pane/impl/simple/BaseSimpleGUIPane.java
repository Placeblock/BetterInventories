package de.placeblock.betterinventories.content.pane.impl.simple;

import de.placeblock.betterinventories.Sizeable;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.content.SearchData;
import de.placeblock.betterinventories.content.item.GUIItem;
import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Base for {@link GUIPane}s where content can be just placed with a {@link Vector2d} as the position.
 * @param <C> Which types of {@link GUISection} can be placed inside the BaseSimpleGUIPane
 * @param <S> The implementing class to return this-type correctly e.g. {@link #addItemEmptySlot(GUIItem)}
 */
@SuppressWarnings("unchecked")
public abstract class BaseSimpleGUIPane<C extends GUISection, S extends BaseSimpleGUIPane<C, S>> extends GUIPane {
    /**
     * The content currently added to this Pane
     */
    protected final List<ChildData<C>> content = new ArrayList<>();

    /**
     * Whether to automatically resize the pane according to the children.
     * If true it will set the size to the bounding box of all children.
     */
    protected final boolean autoSize;

    /**
     * Creates a new BaseSimpleGUIPane
     * @param gui The GUI
     * @param minSize The minimum size of the Pane
     * @param maxSize The maximum size of the Pane
     * @param autoSize Whether to automatically resize the pane according to the children.
     *                 If true it will set the size to the bounding box of all children.
     */
    public BaseSimpleGUIPane(GUI gui, Vector2d minSize, Vector2d maxSize, boolean autoSize) {
        super(gui, minSize, maxSize);
        this.autoSize = autoSize;
    }

    /**
     * Is called to recursively update the size of all {@link GUIPane}s
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSizeRecursive(Sizeable parent) {
        this.updateChildrenRecursive(parent);
        this.updateSize(parent);
    }

    /**
     * Sets the size of the Pane to the bounding-box of all children if auto-size is enabled.
     * @param parent The parent Pane or GUI (Sizeable)
     */
    @Override
    public void updateSize(Sizeable parent) {
        if (this.autoSize) {
            Set<Vector2d> vectors = new HashSet<>();
            for (ChildData<C> childData : this.content) {
                vectors.add(childData.getPosition().add(childData.getChild().getSize()));
            }
            this.setSize(Vector2d.largest(vectors));
        }
    }

    /**
     * @return All children
     */
    @Override
    public List<GUISection> getChildren() {
        return this.content.stream().map(ChildData::getChild).collect(Collectors.toList());
    }

    @Override
    public void onItemProvide(ItemStack item) {
    }

    /**
     * Renders the Pane on a list
     * @return The List
     */
    @Override
    public List<ItemStack> render() {
        List<ItemStack> content = this.getEmptyContentList(ItemStack.class);
        if (!content.isEmpty()) {
            for (ChildData<C> childData : this.content) {
                this.renderOnList(childData.getChild(), childData.getPosition(), content);
            }
        }
        return content;
    }

    /**
     * Searches the GUISection recursively. The SearchData is filled recursively.
     * @param searchData The searchData that contains all needed information
     */
    public void search(SearchData searchData) {
        for (int i = this.content.size()-1; i >= 0; i--) {
            ChildData<C> childData = this.content.get(i);
            Vector2d pos = childData.getPosition();
            Vector2d relativePos = searchData.getRelativePos();
            C section = childData.getChild();
            if (searchData.getPredicate().test(section) && pos.getX() <= relativePos.getX() && pos.getX() + section.getWidth() - 1 >= relativePos.getX()
                    && pos.getY() <= relativePos.getY() && pos.getY() + section.getHeight() - 1 >= relativePos.getY()) {
                searchData.addNode(this);
                searchData.setRelativePos(relativePos.subtract(pos));
                section.search(searchData);
                return;
            }
        }
        searchData.setSection(this);
    }

    /**
     * Adds an Item to an empty slot on the pane.
     * @param item The Item
     * @return this
     */
    @SuppressWarnings("UnusedReturnValue")
    public S addItemEmptySlot(GUIItem item) {
        int nextEmptySlot = this.getNextEmptySlot();
        if (nextEmptySlot == -1) return (S) this;
        this.setSectionAt(nextEmptySlot, (C) item);
        return (S) this;
    }

    /**
     * Fills all empty slots with this Item.
     * @param item The Item
     * @return this
     */
    @SuppressWarnings("UnusedReturnValue")
    public S fill(GUIItem item) {
        while (this.getNextEmptySlot() != -1) {
            this.addItemEmptySlot(item);
        }
        return (S) this;
    }

    /**
     * @return The next empty slot or -1
     */
    public int getNextEmptySlot() {
        for (int i = 0; i < this.getSlots(); i++) {
            SearchData searchData = new SearchData(i, (s) -> true);
            searchData.setRelativePos(this.slotToVector(i));
            this.search(searchData);
            if (this.equals(searchData.getSection())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes a section from the pane.
     * @param section The Section
     * @return Whether the section existed
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean removeSection(C section) {
        ChildData<C> removal = null;
        for (ChildData<C> childData : this.content) {
            if (childData.getChild().equals(section)) {
                removal = childData;
            }
        }
        if (removal != null) {
            this.content.remove(removal);
            return true;
        }
        return false;
    }

    /**
     * Removes a section from the pane
     * @param position The position of the section
     * @return Whether the section existed
     */
    public boolean removeSection(Vector2d position) {
        return this.content.removeIf(childData -> childData.getPosition().equals(position));
    }

    /**
     * Removes all sections from the pane
     * @return this
     */
    @SuppressWarnings("UnusedReturnValue")
    public S clear() {
        this.content.clear();
        return (S) this;
    }

    /**
     * Sets a section at a specific slot.
     * Note that the specified slot is converted to a vector using the pane's width.
     * This can lead to exceptions when the size of this pane is zero.
     * Bear in mind that the vector conversion happens at the time calling this method, which means
     * if you change the size afterward the position will stay the same.
     * If it comes to issues you can use {@link #setSectionAt(Vector2d, GUISection)}
     * @param slot The slot
     * @param section The section
     * @return this
     */
    @SuppressWarnings("UnusedReturnValue")
    public S setSectionAt(int slot, C section) {
        return this.setSectionAt(this.slotToVector(slot), section);
    }

    /**
     * Sets a section at a specific position
     * @param position The position
     * @param section The Section
     * @return this
     */
    public S setSectionAt(Vector2d position, C section) {
        if (section == null) {
            this.removeSection(position);
        } else {
            this.content.add(new ChildData<>(position, section));
        }
        return (S) this;
    }

    /**
     * Sets a section at coordinates 0,0
     * @param section The Section
     * @return this
     */
    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public S setSection(C section) {
        return this.setSectionAt(new Vector2d(), section);
    }

    /**
     * Returns all sections at the given position
     * @param position The position
     * @return All sections at the position
     */
    public Collection<C> getSections(Vector2d position) {
        Collection<C> sections = new ArrayList<>();
        for (ChildData<C> childData : this.content) {
            if (childData.getPosition().equals(position)) {
                sections.add(childData.child);
            }
        }
        return sections;
    }

    /**
     * Used to store children in this Pane
     * @param <C> The type of children
     */
    @Getter
    @AllArgsConstructor
    protected static class ChildData<C extends GUISection> {
        /**
         * The position of the child
         */
        @Setter
        private Vector2d position;
        /**
         * The child {@link GUISection}
         */
        private final C child;
    }

    public static abstract class Builder<B extends Builder<B, P>, P extends BaseSimpleGUIPane<?, ?>> extends GUISection.Builder<B, P> {
        private boolean autoSize;
        public Builder(GUI gui) {
            super(gui);
        }

        public B autoSize(boolean autoSize) {
            this.autoSize = autoSize;
            return (B) this;
        }

        protected boolean isAutoSize() {
            return this.autoSize;
        }
    }
}
