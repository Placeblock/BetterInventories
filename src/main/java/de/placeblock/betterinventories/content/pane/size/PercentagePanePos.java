package de.placeblock.betterinventories.content.pane.size;

import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PercentagePanePos implements PanePos {
    private final double percentageX;
    private final double percentageY;
    private final boolean ceil;

    @Override
    public Vector2d getAbsolute(Vector2d parentSize) {
        return calculateAbsoluteSize(this, parentSize);
    }

    public static Vector2d calculateAbsoluteSize(PercentagePanePos percentagePanePos, Vector2d parent) {
        boolean ceil = percentagePanePos.isCeil();
        double exactX = parent.getX() * percentagePanePos.getPercentageX();
        double exactY = parent.getY() * percentagePanePos.getPercentageY();
        int slotsX = (int) (ceil ? Math.ceil(exactX) : Math.floor(exactX));
        int slotsY = (int) (ceil ? Math.ceil(exactY) : Math.floor(exactY));
        return new Vector2d(slotsX, slotsY);
    }
}
