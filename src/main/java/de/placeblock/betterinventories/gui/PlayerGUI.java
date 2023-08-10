package de.placeblock.betterinventories.gui;

/**
 * Can be used to mark a GUI as a player-specific-GUI
 * @param <P>
 */
@SuppressWarnings("unused")
public interface PlayerGUI<P> {

    P getPlayer();

}