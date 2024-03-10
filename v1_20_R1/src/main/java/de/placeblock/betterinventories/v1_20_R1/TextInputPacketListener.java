package de.placeblock.betterinventories.v1_20_R1;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class TextInputPacketListener implements de.placeblock.betterinventories.nms.TextInputPacketListener {
    private final Player player;
    private final Consumer<String> callback;

    private Channel channel;

    public void register() {
        ServerPlayer serverPlayer = ((CraftPlayer) this.player).getHandle();
        this.channel = serverPlayer.connection.connection.channel;

        this.channel.pipeline().addAfter("decoder", "textInputPacketListener", new MessageToMessageDecoder<Packet>() {
            @Override
            protected void decode(ChannelHandlerContext ctx, Packet msg, List<Object> out) {
                out.add(msg);
                if (msg instanceof ServerboundRenameItemPacket packet) {
                    TextInputPacketListener.this.callback.accept(packet.getName());
                }
            }
        });
    }

    public void unregister() {
        this.channel.pipeline().remove("textInputPacketListener");
    }
}
