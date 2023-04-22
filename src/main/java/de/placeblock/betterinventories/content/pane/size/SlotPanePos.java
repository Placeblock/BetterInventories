package de.placeblock.betterinventories.content.pane.size;

import de.placeblock.betterinventories.util.Vector2d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SlotPanePos implements PanePos {
    private final Vector2d pos;

    @Override
    public Vector2d getAbsolute(Vector2d parentSize) {
        return this.pos;
    }
}
