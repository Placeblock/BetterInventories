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

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class TextInputPacketListener {
    private final TextInputGUI gui;
    private Channel channel;

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

    public void uninject() {
        this.channel.pipeline().remove("textInputPacketListener");
    }
}