package de.placeblock.betterinventories.v1_20_R1;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class NMSBridge implements de.placeblock.betterinventories.nms.NMSBridge {

    @Override
    public de.placeblock.betterinventories.nms.TextInputPacketListener getTextInputPacketListener(Player player, Consumer<String> callback) {
        return new TextInputPacketListener(player, callback);
    }
}
