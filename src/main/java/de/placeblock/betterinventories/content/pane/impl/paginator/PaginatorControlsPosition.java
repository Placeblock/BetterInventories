package de.placeblock.betterinventories.content.pane.impl.paginator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * Author: Placeblock
 */
@Getter
@RequiredArgsConstructor
public enum PaginatorControlsPosition {

    LEFT(size -> new int[]{0, 1}),
    RIGHT(size -> new int[]{size-2, size-1}),
    CENTER(size -> {
        int center = (int) Math.floor(PaginatorControlsPosition.getCenter(size));
        return new int[]{center, center+1};
    }),
    SPACE_BETWEEN(size -> new int[]{0, size-1}),
    SPACE_EVENLY(size -> {
        int value = Math.round((size)/3F)-1;
        return new int[]{value, size-1-value};
    });

    private static float getCenter(Integer size) {
        return (size - 1) / 2F;
    }


    public final Function<Integer, int[]> calculateIndices;

}
