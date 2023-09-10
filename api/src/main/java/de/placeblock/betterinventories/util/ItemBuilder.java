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
    /**
     * The title of the Item
     */
    private final TextComponent title;

    /**
     * The Material of the Item
     */
    private final Material material;

    /**
     * The amount
     */
    private final int amount;

    /**
     * The lore of the Item
     */
    private final List<Component> lore;

    /**
     * The Enchantments of the Item
     */
    private final Map<Enchantment, Integer> enchantments;

    /**
     * The attributes of the Item
     */
    private final Map<Attribute, AttributeModifier> attributes;

    /**
     * The flags of the Item
     */
    private final List<ItemFlag> flags;

    /**
     * Whether the Item is unbreakable
     */
    private boolean unbreakable;

    /**
     * The skinURL of the Head
     */
    private URL skinURL;

    /**
     * Creates a new ItemBuilder
     * @param material The Material of the Item
     */
    public ItemBuilder(Material material) {
        this(null, material);
    }

    /**
     * Creates a new ItemBuilder
     * @param title The title of the Item
     * @param material The Material of the Item
     */
    public ItemBuilder(TextComponent title, Material material) {
        this(title, material, 1);
    }

    /**
     * Creates a new ItemBuilder
     * @param title The title of the Item
     * @param material The Material of the Item
     * @param amount The amount
     */
    public ItemBuilder(TextComponent title, Material material, int amount) {
        this(title, material, amount, true);
    }

    /**
     * Creates a new ItemBuilder
     * @param title The title of the Item
     * @param material The Material of the Item
     * @param amount The amount
     * @param hideInfo Whether to hide general Information about the Item
     */
    public ItemBuilder(TextComponent title, Material material, int amount, boolean hideInfo) {
        this.title = title;
        this.material = material;
        this.amount = amount;
        this.lore = new ArrayList<>();
        this.attributes = new HashMap<>();
        this.flags = new ArrayList<>();
        this.enchantments = new HashMap<>();
        if (hideInfo) {
            this.flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_ENCHANTS).flag(ItemFlag.HIDE_UNBREAKABLE);
        }
    }
    /**
     * Clones another ItemBuilder
     * @param itemBuilder The ItemBuilder to be cloned
     */
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

    /**
     * Sets the lore of the Item
     * @param lore The lore
     * @return this
     */
    public ItemBuilder lore(TextComponent... lore) {
        return this.lore(List.of(lore));
    }

    /**
     * Sets the lore of the Item
     * @param lore The lore
     * @return this
     */
    public ItemBuilder lore(Collection<TextComponent> lore) {
        this.lore.addAll(lore);
        return this;
    }

    /**
     * Adds an enchantment to the Item
     * @param enchantment The Enchantment
     * @param level The level of the Enchantment
     * @return this
     */
    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    /**
     * Adds an attribute to the Item
     * @param attribute The Attribute
     * @param amount The amount to set
     * @param operation The operation
     * @return this
     */
    public ItemBuilder attribute(Attribute attribute, double amount, AttributeModifier.Operation operation) {
        this.attributes.put(attribute, new AttributeModifier(attribute.getKey().getNamespace(), amount, operation));
        return this;
    }

    /**
     * Adds a Flag to the Item
     * @param flag The Flag
     * @return this
     */
    public ItemBuilder flag(ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }

    /**
     * Sets the unbreakable tag of the Item
     * @param unbreakable Whether the Item is unbreakable
     * @return self
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    /**
     * Sets the skin-texture of the Item
     * Only effective when using {@link Material#PLAYER_HEAD} as type
     * @param skinTexture The skin-texture
     * @return this
     */
    public ItemBuilder skinTexture(URL skinTexture) {
        this.skinURL = skinTexture;
        return this;
    }

    /**
     * Builds the ItemStack
     * @return The ItemStack
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount);

        if (this.title != null || !this.lore.isEmpty() || !this.enchantments.isEmpty() || !this.attributes.isEmpty()
            || !this.flags.isEmpty() || this.unbreakable || this.skinURL != null) {
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
        }
        return item;
    }
}
