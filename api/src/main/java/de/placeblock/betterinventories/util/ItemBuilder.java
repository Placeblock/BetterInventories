package de.placeblock.betterinventories.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.*;

/**
 * Can be used to create more complex Items easier.
 */
@SuppressWarnings("unused")
public class ItemBuilder {
    private final TextComponent title;
    private final Material material;
    private final int amount;
    private final List<Component> lore;
    private final Map<Enchantment, Integer> enchantments;
    private final Map<Attribute, AttributeModifier> attributes;
    private final List<ItemFlag> flags;
    private boolean unbreakable;
    private URL skinURL;

    public ItemBuilder(TextComponent title, Material material) {
        this(title, material, 1);
    }

    public ItemBuilder(TextComponent title, Material material, int amount) {
        this(title, material, amount, true);
    }

    public ItemBuilder(TextComponent title, Material material, int amount, boolean unbreakable) {
        this(title, material, amount, unbreakable, true);
    }

    public ItemBuilder(TextComponent title, Material material, int amount, boolean unbreakable, boolean hideInfo) {
        this.title = title;
        this.material = material;
        this.amount = amount;
        this.unbreakable = unbreakable;
        this.lore = new ArrayList<>();
        this.attributes = new HashMap<>();
        this.flags = new ArrayList<>();
        this.enchantments = new HashMap<>();
        if (hideInfo) {
            this.flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_ENCHANTS).flag(ItemFlag.HIDE_UNBREAKABLE);
        }
    }

    public ItemBuilder(ItemBuilder itemBuilder) {
        this.title = itemBuilder.title;
        this.material = itemBuilder.material;
        this.lore = new ArrayList<>(itemBuilder.lore);
        this.amount = itemBuilder.amount;
        this.enchantments = new HashMap<>(itemBuilder.enchantments);
        this.flags = new ArrayList<>(itemBuilder.flags);
        this.attributes = new HashMap<>(itemBuilder.attributes);
        this.unbreakable = itemBuilder.unbreakable;
        this.skinURL = itemBuilder.skinURL;
    }

    public ItemBuilder lore(TextComponent... lore) {
        return this.lore(List.of(lore));
    }

    public ItemBuilder lore(Collection<TextComponent> lore) {
        this.lore.addAll(lore);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder attribute(Attribute attribute, double amount, AttributeModifier.Operation operation) {
        this.attributes.put(attribute, new AttributeModifier(attribute.getKey().getNamespace(), amount, operation));
        return this;
    }

    public ItemBuilder flag(ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder skinTexture(URL skinTexture) {
        this.skinURL = skinTexture;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(this.title);
        meta.lore(this.lore);
        for (Enchantment enchantment : this.enchantments.keySet()) {
            meta.addEnchant(enchantment, this.enchantments.get(enchantment), true);
        }
        for (Attribute attribute : this.attributes.keySet()) {
            meta.addAttributeModifier(attribute, this.attributes.get(attribute));
        }
        for (ItemFlag flag : this.flags) {
            meta.addItemFlags(flag);
        }
        if (this.unbreakable) {
            meta.setUnbreakable(true);
        }
        if (meta instanceof SkullMeta skullMeta && this.skinURL != null) {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(this.skinURL);
            profile.setTextures(textures);
            skullMeta.setPlayerProfile(profile);
        }

        item.setItemMeta(meta);
        return item;
    }
}