package de.placeblock.betterinventories.gui;

/**
 * Can be used to mark a GUI as a player-specific-GUI
 * @param <P> The type of the Player
 */
@SuppressWarnings("unused")
public interface PlayerGUI<P> {

    /**
     * @return The player
     */
    P getPlayer();

}