package de.placeblock.betterinventories.gui.impl.textinput;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The packet-listener used by {@link TextInputGUI} to receive anvil text
 */
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class TextInputPacketListener {
    /**
     * The according {@link TextInputGUI}
     */
    private final TextInputGUI gui;

    /**
     * The channel where the listener is added
     */
    private Channel channel;

    /**
     * Injects the listener into the pipeline of the players connection
     */
    public void inject() {
        Player player = this.gui.getPlayer();
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        this.channel = serverPlayer.connection.connection.channel;
        //noinspection rawtypes
        this.channel.pipeline().addAfter("decoder", "textInputPacketListener", new MessageToMessageDecoder<Packet>() {
            @Override
            protected void decode(ChannelHandlerContext ctx, Packet msg, List<Object> out) {
                out.add(msg);
                if (msg instanceof ServerboundRenameItemPacket packet) {
                    TextInputPacketListener.this.gui.updateText(packet.getName());
                }
            }
        });
    }

    /**
     * Uninjects the listener
     */
    public void uninject() {
        this.channel.pipeline().remove("textInputPacketListener");
    }
}