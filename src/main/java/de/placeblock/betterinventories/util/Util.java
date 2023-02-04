package de.placeblock.betterinventories.util;

import de.placeblock.betterinventories.content.item.ItemBuilder;
import io.schark.design.texts.Texts;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Author: Placeblock
 */
public class Util {
    public static ItemStack getArrowItem(ArrowDirection direction, TextComponent title) {
        return new ItemBuilder(title, Material.PLAYER_HEAD).skinTexture(direction.getTexture()).build();
    }

    public static ItemStack getArrowItem(ArrowDirection direction) {
        return Util.getArrowItem(direction, Texts.primary(direction.getName()));
    }

    public static void getTexture(Plugin plugin, String playerName, Consumer<URL> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            callback.accept(offlinePlayer.getPlayerProfile().getTextures().getSkin());
        });
    }

    public static Vector2d calculateGUISize(Collection<?> items, int width) {
        return new Vector2d(width, (int) Math.ceil(items.size()*1F/width));
    }

    public static Vector2d slotToVector(int index, int width) {
        return new Vector2d(index % width, (int) Math.floor(index/(width*1F)));
    }

    public static int vectorToSlot(Vector2d vector, int width) {
        return vector.getY()*width+vector.getX();
    }

    public static int modulo(int divident, int divisor) {
        return (((divident % divisor) + divisor) % divisor);
    }

    public static URL convertURL(String base64) {
        try {
            return new URL(new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
