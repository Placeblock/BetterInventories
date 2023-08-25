package de.placeblock.betterinventories.builder.gui;

import de.placeblock.betterinventories.builder.Builder;
import de.placeblock.betterinventories.gui.GUI;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


/**
 * Base class for creating GUI-Builders
 * @param <G> The type of GUI
 * @param <B> The type of Builder
 */
@SuppressWarnings({"unchecked", "unused"})
@RequiredArgsConstructor
public abstract class BaseGUIBuilder<G extends GUI, B extends BaseGUIBuilder<G, B>> implements Builder<G, B> {
    /**
     * The plugin for the GUI
     */
    private final Plugin plugin;

    /**
     * The title of the GUI
     */
    private TextComponent title;

    /**
     * The type of the GUI
     */
    private InventoryType type;

    /**
     * Sets the title of the Inventory.
     * @param title The title
     * @return this
     */
    public B title(@NotNull TextComponent title) {
        this.title = title;
        return (B) this;
    }

    /**
     * Sets the type of the Inventory.
     * Does not need to be set in many implementing GUIBuilders.
     * @param type The type
     * @return this
     */
    public B type(@NotNull InventoryType type) {
        this.type = type;
        return (B) this;
    }

    /**
     * @return The title of the GUI
     */
    protected TextComponent getTitle() {
        return this.getValue(this.title);
    }

    /**
     * @return The type of the GUI
     */
    protected InventoryType getType() {
        return this.getValue(this.type);
    }

    /**
     * @return The plugin for the GUI
     */
    protected Plugin getPlugin() {
        return this.plugin;
    }


    /**
     * Builds the GUI
     * @return The new GUI
     */
    public abstract G build();
}
