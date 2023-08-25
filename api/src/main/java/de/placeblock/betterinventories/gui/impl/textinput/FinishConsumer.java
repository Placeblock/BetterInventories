package de.placeblock.betterinventories.gui.impl.textinput;

/**
 * Used by the TextInputGUI and is called when a player clicks on the submit-item
 */
@FunctionalInterface
public interface FinishConsumer {
    /**
     * The method called when the player clicks on the submit-item
     * @param finalText The text the player entered
     * @param abort Whether the player aborted, e.g. by closing the Inventory
     * @return true if the inventory should get closed
     */
    boolean accept(String finalText, boolean abort);

}
