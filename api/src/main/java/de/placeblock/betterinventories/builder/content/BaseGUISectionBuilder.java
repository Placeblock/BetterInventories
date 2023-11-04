package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.builder.Builder;
import de.placeblock.betterinventories.content.GUISection;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;
import lombok.RequiredArgsConstructor;

/**
 * Base class for creating GUISection-Builders
 * @param <G> The GUISection type
 * @param <B> The Builder type
 */
@Deprecated(forRemoval = true)
@SuppressWarnings({"unchecked", "unused"})
@RequiredArgsConstructor
public abstract class BaseGUISectionBuilder<G extends GUISection, B extends BaseGUISectionBuilder<G, B>> implements Builder<G, B> {
    /**
     * The GUI this Sections belongs to
     */
    private final GUI gui;

    /**
     * The size of this section
     */
    private Vector2d size;

    /**
     * Sets the size of the {@link GUISection}.
     * Not needed in many implementations.
     * @param size The size
     * @return this
     */
    public B size(Vector2d size) {
        this.size = size;
        return (B) this;
    }

    /**
     * @return The size of the Section
     */
    protected Vector2d getSize() {
        return this.size;
    }

    /**
     * @return The GUI for this Section
     */
    protected GUI getGui() {
        return this.gui;
    }
}
