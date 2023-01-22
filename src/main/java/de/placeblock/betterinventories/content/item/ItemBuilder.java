package de.placeblock.betterinventories.content.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Placeblock
 */
@SuppressWarnings("unused")
public class ItemBuilder {
    private final Component title;
    private final Material material;
    private final int amount;
    private final List<Component> lore = new ArrayList<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private final Map<Attribute, AttributeModifier> attributes = new HashMap<>();
    private final List<ItemFlag> flags = new ArrayList<>();
    private boolean unbreakable;

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

    public ItemBuilder lore(TextComponent lore) {
        this.lore.add(lore);
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

        item.setItemMeta(meta);
        return item;
    }

}
