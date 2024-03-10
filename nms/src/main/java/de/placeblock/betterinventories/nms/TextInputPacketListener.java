package de.placeblock.betterinventories.nms;

import org.bukkit.entity.Player;

/**
 * TextInputPacketListener for TextInputGUIs
 * Receives Text Input Updates
 */
public interface TextInputPacketListener {
    /**
     * The Player of the PacketListener
     * @return The Player
     */
    Player getPlayer();

    /**
     * Registers this Listener
     */
    void register();

    /**
     * Unregisters this Listener
     */
    void unregister();

}
