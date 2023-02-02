package de.placeblock.betterinventories.util;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import io.schark.design.texts.Texts;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

/**
 * Author: Placeblock
 */
public class Util {
    public static ItemStack getArrowItem(ArrowDirection direction, TextComponent title) {
        ItemStack item = new ItemBuilder(title, Material.PLAYER_HEAD).build();
        return Util.setHeadTexture(item, direction.getTexture());
    }

    public static ItemStack getArrowItem(ArrowDirection direction) {
        return Util.getArrowItem(direction, Texts.primary(direction.getName()));
    }

    public static ItemStack setHeadTexture(ItemStack item, String texture) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        PlayerProfile playerProfile = new CraftPlayerProfile(UUID.randomUUID(), "");
        playerProfile.setProperty(new ProfileProperty("textures", texture));
        meta.setPlayerProfile(playerProfile);
        item.setItemMeta(meta);
        return item;
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

}
