package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.content.pane.impl.HorizontalSplitGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;

/**
 * Builder for creating HorizontalSplitGUIPanes
 */
@Deprecated(forRemoval = true)
@Getter
@SuppressWarnings("unused")
public class HorizontalSplitGUIPaneBuilder extends BaseGUIPaneBuilder<HorizontalSplitGUIPane, HorizontalSplitGUIPaneBuilder> {
    /**
     * The upper pane
     */
    private GUIPane upperPane;

    /**
     * The lower pane
     */
    private GUIPane lowerPane;

    /**
     * Creates a new HorizontalSplitGUIPaneBuilder
     * @param gui The GUI for the Pane
     */
    public HorizontalSplitGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    /**
     * Sets the upper part of the pane
     * @param pane The upper pane
     * @return this
     */
    public HorizontalSplitGUIPaneBuilder upperPane(GUIPane pane) {
        this.upperPane = pane;
        return this;
    }

    /**
     * Sets the lower part of the pane
     * @param pane The lower pane
     * @return this
     */
    public HorizontalSplitGUIPaneBuilder lowerPane(GUIPane pane) {
        this.lowerPane = pane;
        return this;
    }

    @Override
    public HorizontalSplitGUIPane build() {
        HorizontalSplitGUIPane horizontalSplitGUIPane = new HorizontalSplitGUIPane(this.getGui(), this.getBestMinSize(), this.getBestMaxSize());
        horizontalSplitGUIPane.setUpperPane(this.getValue(this::getUpperPane));
        horizontalSplitGUIPane.setLowerPane(this.getValue(this::getLowerPane));
        return horizontalSplitGUIPane;
    }
}
