package de.placeblock.betterinventories.content.pane.impl.transfer;

import de.placeblock.betterinventories.content.pane.impl.simple.SimpleItemGUIPane;
import de.placeblock.betterinventories.gui.GUI;
import de.placeblock.betterinventories.util.Vector2d;

/**
 * GUIPane which allows Items to be inserted and taken out
 */
@SuppressWarnings("unused")
public class TransferGUIPane extends SimpleItemGUIPane {
    /**
     * Creates a new TransferGUIPane
     * @param gui The GUI for the Pane
     * @param size The size of the Pane
     */
    public TransferGUIPane(GUI gui, Vector2d size) {
        super(gui, size, size);
    }
}
