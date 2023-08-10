package de.placeblock.betterinventories.content.pane.impl.paginator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * Different positions for the {@link PaginatorGUIPane}'s Controls.
 * Get calculated based on the size of the {@link PaginatorGUIPane}.
 */
@Getter
@RequiredArgsConstructor
public enum PaginatorControlsPosition {
    /**
     * Will place both controls on the left.
     */
    LEFT(size -> new int[]{0, 1}),
    /**
     * Will place both controls on the right.
     */
    RIGHT(size -> new int[]{size-2, size-1}),
    /**
     * Will place both controls in the center.
     */
    CENTER(size -> {
        int center = (int) Math.floor(PaginatorControlsPosition.getCenter(size));
        return new int[]{center, center+1};
    }),
    /**
     * Will place one control on the left and one on the right.
     */
    SPACE_BETWEEN(size -> new int[]{0, size-1}),
    /**
     * Will place them in a way that the space between the controls and one control to a side is equal.
     */
    SPACE_EVENLY(size -> {
        int value = Math.round((size)/3F)-1;
        return new int[]{value, size-1-value};
    });

    private static float getCenter(Integer size) {
        return (size - 1) / 2F;
    }


    public final Function<Integer, int[]> calculateIndices;

}
