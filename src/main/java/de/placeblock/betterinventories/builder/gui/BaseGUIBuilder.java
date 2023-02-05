package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.builder.Builder;
import de.placeblock.betterinventories.gui.GUI;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings({"unchecked", "unused"})
@RequiredArgsConstructor
public abstract class BaseGUIBuilder<G extends GUI, B extends BaseGUIBuilder<G, B>> implements Builder<G, B> {
    private final Plugin plugin;
    private TextComponent title;
    private InventoryType type;

    public B title(@NotNull TextComponent title) {
        this.title = title;
        return (B) this;
    }

    public B type(@NotNull InventoryType type) {
        this.type = type;
        return (B) this;
    }

    protected TextComponent getTitle() {
        if (this.title == null) {
            throw new IllegalStateException("Title is missing in builder");
        }
        return this.title;
    }

    protected InventoryType getType() {
        if (this.type == null) {
            throw new IllegalStateException("InventoryType is missing in builder");
        }
        return this.type;
    }

    protected Plugin getPlugin() {
        return this.plugin;
    }

    public abstract G build();
}
