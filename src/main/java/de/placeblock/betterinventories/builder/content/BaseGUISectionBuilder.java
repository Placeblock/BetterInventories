package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.builder.Builder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import lombok.RequiredArgsConstructor;

/**
 * Author: Placeblock
 */
@SuppressWarnings({"unchecked", "unused"})
@RequiredArgsConstructor
public abstract class BaseGUISectionBuilder<G extends GUISection, B extends BaseGUISectionBuilder<G, B>> implements Builder<G, B> {
    private final GUI gui;
    private Integer width;
    private Integer height;

    public B width(Integer width) {
        this.width = width;
        return (B) this;
    }

    public B height(Integer height) {
        this.height = height;
        return (B) this;
    }

    protected int getWidth() {
        if (this.width == null) {
            throw new IllegalStateException("Width is null in builder");
        }
        return this.width;
    }

    protected int getHeight() {
        if (this.height == null) {
            throw new IllegalStateException("Height is null in builder");
        }
        return this.height;
    }

    protected GUI getGui() {
        return this.gui;
    }
}
