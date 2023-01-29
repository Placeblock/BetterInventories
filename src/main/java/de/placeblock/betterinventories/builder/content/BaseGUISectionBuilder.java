package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.builder.Builder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.RequiredArgsConstructor;

/**
 * Author: Placeblock
 */
@SuppressWarnings({"unchecked", "unused"})
@RequiredArgsConstructor
public abstract class BaseGUISectionBuilder<G extends GUISection, B extends BaseGUISectionBuilder<G, B>> implements Builder<G, B> {
    private final GUI gui;
    private Vector2d size;

    public B width(Vector2d size) {
        this.size = size;
        return (B) this;
    }

    protected Vector2d getSize() {
        if (this.size == null) {
            throw new IllegalStateException("Size is null in builder");
        }
        return this.size;
    }

    protected GUI getGui() {
        return this.gui;
    }
}
