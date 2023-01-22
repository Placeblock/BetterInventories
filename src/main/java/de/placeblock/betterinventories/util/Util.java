package de.placeblock.betterinventories.util;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import de.placeblock.betterinventories.content.item.ItemBuilder;
import io.schark.design.texts.Texts;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

/**
 * Author: Placeblock
 */
public class Util {

    public static ItemStack getArrowItem(ArrowDirection direction) {
        ItemStack item = new ItemBuilder(Texts.primary(direction.getName()), Material.PLAYER_HEAD).build();
        return Util.setHeadTexture(item, direction.getTexture());
    }

    public static ItemStack setHeadTexture(ItemStack item, String texture) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        PlayerProfile playerProfile = new CraftPlayerProfile(UUID.randomUUID(), "");
        playerProfile.setProperty(new ProfileProperty("textures", texture));
        meta.setPlayerProfile(playerProfile);
        item.setItemMeta(meta);
        return item;
    }

    public static Vector2d slotToPosition(int index, int width) {
        return new Vector2d(index % width, (int) Math.floor(index/(width*1F)));
    }

    public static int modulo(int divident, int divisor) {
        return (((divident % divisor) + divisor) % divisor);
    }

}
