package de.placeblock.betterinventories.content;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Returned by search methods.
 */
@Getter
public class SearchData {
    /**
     * The slot to start with searching
     */
    private final int slot;
    /**
     * Gets called for every section to test if the search should continue.
     * If the predicate returns false the current section is returned.
     */
    private final SearchPredicate predicate;

    /**
     * The found section.
     */
    @Setter
    private GUISection section;

    /**
     * The position of the section relative to its parent.
     * Must be updated by every pane if it passes this object to its children.
     */
    @Setter
    private Vector2d relativePos;

    /**
     * While searching a path is constructed
     */
    private final List<GUIPane> path = new ArrayList<>();

    /**
     * Creates new SearchData with initial values
     * @param slot The slot to search for
     * @param predicate The predicate that is executed for each pane
     */
    public SearchData(int slot, SearchPredicate predicate) {
        this.slot = slot;
        this.predicate = predicate;
    }

    /**
     * Adds a new node to the path.
     * Must be updated by every pane if it passes this object to its children.
     * @param pane The node
     */
    public void addNode(GUIPane pane) {
        this.path.add(pane);
    }

}
