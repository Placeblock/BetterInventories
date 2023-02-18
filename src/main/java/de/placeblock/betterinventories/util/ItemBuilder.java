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


@SuppressWarnings("unused")
public class ItemBuilder implements Cloneable {
    private TextComponent title;
    private Material material;
    private int amount;
    private List<Component> lore = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private Map<Attribute, AttributeModifier> attributes = new HashMap<>();
    private List<ItemFlag> flags = new ArrayList<>();
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
        if (hideInfo) {
            this.flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_ENCHANTS).flag(ItemFlag.HIDE_UNBREAKABLE);
        }
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

    @Override
    public ItemBuilder clone() {
        try {
            ItemBuilder clone = (ItemBuilder) super.clone();
            clone.title = this.title;
            clone.material = this.material;
            clone.lore = this.lore;
            clone.amount = this.amount;
            clone.enchantments = this.enchantments;
            clone.flags = this.flags;
            clone.attributes = this.attributes;
            clone.unbreakable = this.unbreakable;
            clone.skinURL = this.skinURL;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
