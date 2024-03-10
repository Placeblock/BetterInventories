package de.placeblock.betterinventories.nms;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Contains all methods that need nms for implementation
 */
public interface NMSBridge {

    /**
     * Returns a new TextInputPacketListener
     * @param player The Player
     * @return The new PacketListener
     */
    TextInputPacketListener getTextInputPacketListener(Player player, Consumer<String> callback);

}
