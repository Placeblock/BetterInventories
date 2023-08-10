package de.placeblock.betterinventories.builder.content;

import de.placeblock.betterinventories.content.pane.GUIPane;
import de.placeblock.betterinventories.content.pane.impl.HorizontalSplitGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class HorizontalSplitGUIPaneBuilder extends BaseGUIPaneBuilder<HorizontalSplitGUIPane, HorizontalSplitGUIPaneBuilder> {
    private GUIPane upperPane;
    private GUIPane lowerPane;

    public HorizontalSplitGUIPaneBuilder(GUI gui) {
        super(gui);
    }

    public HorizontalSplitGUIPaneBuilder upperPane(GUIPane pane) {
        this.upperPane = pane;
        return this;
    }

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
