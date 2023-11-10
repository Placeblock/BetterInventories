package de.placeblock.betterinventories.gui.impl;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AnvilGUIs are standard BaseAnvilGUIs with basic functionality.
 */
public class AnvilGUI extends BaseAnvilGUI {
    /**
     * Creates a new AnvilGUI
     *
     * @param plugin The plugin
     * @param title  The title of the GUI
     * @param removeItems Whether to remove loose items on close.
     *                   The first player that closes the gui gets the items
     */
    @Deprecated(forRemoval = true)
    public AnvilGUI(Plugin plugin, TextComponent title, boolean removeItems) {
        super(plugin, title, removeItems);
    }

    /**
     * Builder for AnvilGUIs
     * @param <P> The plugin that uses the builder
     */
    public static class Builder<P extends JavaPlugin> extends BaseAnvilGUI.Builder<Builder<P>, AnvilGUI, P> {

        /**
         * Creates a new Builder
         * @param plugin The plugin that uses the builder
         */
        public Builder(P plugin) {
            super(plugin);
        }

        @Override
        public AnvilGUI build() {
            return new AnvilGUI(this.getPlugin(), this.getTitle(), this.isRemoveItems());
        }

        @Override
        protected Builder<P> self() {
            return this;
        }
    }
}
