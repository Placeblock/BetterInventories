package de.placeblock.betterinventories.gui.impl;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AnvilGUI extends BaseAnvilGUI {
    /**
     * Creates a new AnvilGUI
     *
     * @param plugin The plugin
     * @param title  The title of the GUI
     */
    @Deprecated(forRemoval = true)
    public AnvilGUI(Plugin plugin, TextComponent title, boolean removeItems) {
        super(plugin, title, removeItems);
    }

    public static class Builder<P extends JavaPlugin> extends BaseAnvilGUI.Builder<Builder<P>, AnvilGUI, P> {

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
